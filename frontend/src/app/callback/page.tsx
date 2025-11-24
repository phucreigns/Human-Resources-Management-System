"use client";

import { useEffect, useState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import apiClient from "@/lib/api";

export default function CallbackPage() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const { setAuthData } = useAuth();
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const code = searchParams.get("code");
    const errorParam = searchParams.get("error");

    if (errorParam) {
      setError(errorParam);
      setTimeout(() => {
        router.push("/login");
      }, 3000);
      return;
    }

    if (!code) {
      setError("Authorization code not found");
      setTimeout(() => {
        router.push("/login");
      }, 3000);
      return;
    }

    // Clear any old session data before processing new login
    // This ensures we don't mix old and new user data
    const oldToken = sessionStorage.getItem("accessToken");
    const oldUser = sessionStorage.getItem("user");
    if (oldToken || oldUser) {
      console.log("Clearing old session before new login");
      sessionStorage.removeItem("accessToken");
      sessionStorage.removeItem("refreshToken");
      sessionStorage.removeItem("user");
    }

    // Exchange code for token
    const exchangeCode = async () => {
      try {
        const response = await apiClient.get(`/auth/login?code=${code}`);
        // Response structure: { access_token, refresh_token, expires_in } (snake_case)
        const tokenData = response.data;
        const accessToken = tokenData.access_token || tokenData.accessToken;
        const refreshToken = tokenData.refresh_token || tokenData.refreshToken;

        if (refreshToken) {
          // Use sessionStorage instead of localStorage so each tab has its own session
          sessionStorage.setItem("refreshToken", refreshToken);
        }

        // Decode JWT token to get user info (simple decode without verification for client-side)
        try {
          const tokenParts = accessToken.split(".");
          if (tokenParts.length === 3) {
            const payload = JSON.parse(atob(tokenParts[1]));
            
            // Check if email is verified
            const emailVerified = payload.email_verified;
            const userEmail = payload.email || payload.sub;
            
            if (!emailVerified) {
              // Redirect to waiting-for-email-verification page
              router.push(`/waiting-for-email-verification?email=${encodeURIComponent(userEmail)}`);
              return;
            }
            
            const user = {
              email: userEmail,
              name: payload.name || payload.nickname || payload.email,
              picture: payload.picture,
            };
            setAuthData(accessToken, user);
            router.push("/dashboard");
          } else {
            throw new Error("Invalid token format");
          }
        } catch (decodeError) {
          console.error("Error decoding token:", decodeError);
          // If decode fails, try to proceed but log the error
          const user = {
            email: "user@example.com",
            name: "User",
          };
          setAuthData(accessToken, user);
          router.push("/dashboard");
        }
      } catch (err: any) {
        console.error("Login error:", err);
        const errorMessage = err.response?.data?.message || err.message || "Failed to authenticate. Please try again.";
        setError(errorMessage);
        
        // Show alert for expired/invalid code
        if (errorMessage.includes("expired") || errorMessage.includes("invalid") || errorMessage.includes("Authorization code")) {
          alert(`Login failed: ${errorMessage}`);
        }
        
        setTimeout(() => {
          router.push("/login");
        }, 3000);
      }
    };

    exchangeCode();
  }, [searchParams, router, setAuthData]);

  if (error) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-white">
        <div className="max-w-md w-full bg-white rounded-lg shadow-lg p-8 text-center">
          <div className="w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg
              className="w-8 h-8 text-red-600"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </div>
          <h2 className="text-2xl font-bold text-gray-900 mb-2">
            Đăng nhập thất bại
          </h2>
          <p className="text-gray-600 mb-4">{error}</p>
          <p className="text-sm text-gray-500">
            Đang chuyển hướng về trang đăng nhập...
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-white">
      <div className="text-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4"></div>
        <h2 className="text-xl font-semibold text-gray-900 mb-2">
          Đang xử lý đăng nhập...
        </h2>
        <p className="text-gray-600">Vui lòng đợi trong giây lát</p>
      </div>
    </div>
  );
}

