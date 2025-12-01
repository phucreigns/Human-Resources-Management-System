"use client";

import { Header } from "@/components/common/Header";
import { Footer } from "@/components/common/Footer";
import { Hero } from "@/components/common/Hero";
import { Features } from "@/components/common/Features";
import { Services } from "@/components/common/Services";
import { Projects } from "@/components/common/Projects";
import { Introduction } from "@/components/common/Introduction";
import { Reviews } from "@/components/common/Reviews";
import { Technology } from "@/components/common/Technology";
import { FAQ } from "@/components/common/FAQ";
import { useEffect, useState } from "react";
import { useSearchParams, useRouter } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import apiClient from "@/lib/api";

export default function Home() {
  const searchParams = useSearchParams();
  const router = useRouter();
  const { setAuthData, isAuthenticated } = useAuth();
  const [isProcessing, setIsProcessing] = useState(false);

  useEffect(() => {
    const code = searchParams.get("code");
    const error = searchParams.get("error");

    // Nếu có code từ Auth0 callback và chưa có token, xử lý đăng nhập
    const hasToken = sessionStorage.getItem("accessToken");
    const processedCode = sessionStorage.getItem("processedCode");
    
    // Only process if:
    // 1. Code exists
    // 2. Not currently processing
    // 3. No token exists
    // 4. This code hasn't been processed before
    if (code && !isProcessing && !hasToken && code !== processedCode) {
      // Mark this code as being processed immediately
      sessionStorage.setItem("processedCode", code);
      setIsProcessing(true);
      handleAuthCallback(code);
    } else if (error) {
      console.error("Auth0 error:", error);
      // Clean up URL
      const url = new URL(window.location.href);
      url.searchParams.delete("error");
      url.searchParams.delete("error_description");
      window.history.replaceState({}, "", url.pathname);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [searchParams]);

  const handleAuthCallback = async (code: string) => {
    try {
      const response = await apiClient.get(`/login?code=${code}`);
      const tokenData = response.data;
      
      // Backend returns snake_case (access_token, refresh_token) due to @JsonNaming annotation
      const accessToken = tokenData.access_token || tokenData.accessToken;
      const refreshToken = tokenData.refresh_token || tokenData.refreshToken;

      if (!accessToken) {
        throw new Error("No access token received from server");
      }

      if (refreshToken) {
        sessionStorage.setItem("refreshToken", refreshToken);
      }

      // Decode JWT token to get user info
      try {
        const tokenParts = accessToken.split(".");
        if (tokenParts.length === 3) {
          const payload = JSON.parse(atob(tokenParts[1]));
          const roles = payload["https://xuanphuchrms.com/roles"] || payload.roles || [];
          const user = {
            email: payload.email || payload.sub,
            name: payload.name || payload.nickname || payload.email,
            picture: payload.picture,
            roles: Array.isArray(roles) ? roles : [],
            phoneNumber: payload.phone_number || payload.phoneNumber,
            position: payload.position || payload["https://xuanphuchrms.com/position"],
          };
          
          // Set auth data first
          setAuthData(accessToken, user);
          console.log("Auth data set successfully:", { email: user.email, name: user.name });
          
          // Remove code from URL immediately to prevent re-processing
          const url = new URL(window.location.href);
          url.searchParams.delete("code");
          window.history.replaceState({}, "", url.pathname);
          
          // Clear processed code from sessionStorage
          sessionStorage.removeItem("processedCode");
          
          // Small delay to ensure state is persisted, then reload
          setTimeout(() => {
            window.location.reload();
          }, 300);
        } else {
          throw new Error("Invalid token format");
        }
      } catch (decodeError) {
        console.error("Token decode error:", decodeError);
        // Fallback: create user with email from token if possible
        const user = {
          email: "user@example.com",
          name: "User",
        };
        setAuthData(accessToken, user);
        console.log("Auth data set (fallback):", { accessToken, user });
        const url = new URL(window.location.href);
        url.searchParams.delete("code");
        window.history.replaceState({}, "", url.pathname);
        setTimeout(() => {
          window.location.reload();
        }, 300);
      }
    } catch (err: any) {
      console.error("Login error:", err);
      
      // Handle different error types
      if (err.code === 'ERR_NETWORK' || err.message?.includes('ERR_CONNECTION_REFUSED')) {
        alert("Cannot connect to backend server. Please make sure auth-service is running on port 8090.");
      } else if (err.response?.status === 500 || err.response?.data?.message?.includes('Invalid authorization code')) {
        alert("Login failed: Authorization code expired or invalid. Please try logging in again.");
      } else if (err.response?.data?.message) {
        alert(`Login failed: ${err.response.data.message}`);
      } else {
        alert("Login failed. Please try again.");
      }
      
      // Clean up URL and reset processing state
      const url = new URL(window.location.href);
      url.searchParams.delete("code");
      window.history.replaceState({}, "", url.pathname);
      
      // Clear processed code from sessionStorage on error
      sessionStorage.removeItem("processedCode");
      
      setIsProcessing(false);
    }
  };

  // Nếu đang xử lý callback, hiển thị loading
  if (isProcessing) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-white">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500 mx-auto mb-4"></div>
          <h2 className="text-xl font-semibold text-gray-900 mb-2">
            Processing login...
          </h2>
          <p className="text-gray-600">Please wait a moment</p>
        </div>
      </div>
    );
  }

  return (
    <main className="min-h-screen">
      <Header />
      <Hero />
      <Features />
      <Introduction />
      <Reviews />
      <Technology />
      <FAQ />
      <Services />
      <Projects />
      <Footer />
    </main>
  );
}

