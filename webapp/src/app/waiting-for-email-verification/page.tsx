"use client";

import { useEffect, useState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import Link from "next/link";
import { useAuth } from "@/context/AuthContext";

export default function WaitingForEmailVerificationPage() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const { login, isAuthenticated } = useAuth();
  const [email, setEmail] = useState<string | null>(null);
  const [countdown, setCountdown] = useState(30);
  const [canResend, setCanResend] = useState(false);

  useEffect(() => {
    // Get email from URL params if available
    const emailParam = searchParams.get("email");
    if (emailParam) {
      setEmail(emailParam);
    }

    // If user is already authenticated, redirect to dashboard
    if (isAuthenticated) {
      router.push("/dashboard");
    }
  }, [searchParams, isAuthenticated, router]);

  useEffect(() => {
    // Countdown timer for resend button
    if (countdown > 0) {
      const timer = setTimeout(() => setCountdown(countdown - 1), 1000);
      return () => clearTimeout(timer);
    } else {
      setCanResend(true);
    }
  }, [countdown]);

  const handleResendVerification = () => {
    if (!canResend) return;

    // Redirect to Auth0 login to trigger email verification flow
    // Auth0 will handle resending verification email
    const redirectUri = "http://localhost:3000";
    const auth0Url = `http://dev-kvwfevkuw0dqnvc2.us.auth0.com/authorize?client_id=JQGQbJkzbYwd16ykAGBJphm4UViy1H4h&response_type=code&redirect_uri=${encodeURIComponent(
      redirectUri
    )}&scope=openid%20email%20profile%20offline_access&audience=https://xuanphuchrms.com/&prompt=login`;
    window.location.href = auth0Url;
  };

  const handleCheckVerification = () => {
    // Try to login again to check if email is verified
    login();
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-white py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        <div className="bg-white rounded-lg shadow-xl p-8">
          {/* Icon */}
          <div className="flex justify-center mb-6">
            <div className="w-20 h-20 bg-gradient-to-br from-blue-500 via-cyan-500 to-indigo-500 rounded-full flex items-center justify-center shadow-lg">
              <svg
                className="w-10 h-10 text-white"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"
                />
              </svg>
            </div>
          </div>

          {/* Title */}
          <h2 className="text-center text-2xl font-extrabold text-gray-900 mb-2">
            Kiểm tra email của bạn
          </h2>
          <p className="text-center text-sm text-gray-600 mb-6">
            Chúng tôi đã gửi một email xác thực đến địa chỉ email của bạn
          </p>

          {/* Email Display */}
          {email && (
            <div className="mb-6 p-3 bg-blue-50 rounded-lg border border-blue-200">
              <p className="text-sm text-gray-700 text-center">
                <span className="font-semibold">Email:</span> {email}
              </p>
            </div>
          )}

          {/* Instructions */}
          <div className="mb-6 space-y-3">
            <div className="flex items-start">
              <div className="flex-shrink-0">
                <div className="w-6 h-6 bg-cyan-100 rounded-full flex items-center justify-center">
                  <span className="text-cyan-600 text-xs font-bold">1</span>
                </div>
              </div>
              <p className="ml-3 text-sm text-gray-700">
                Mở hộp thư đến của bạn và tìm email từ chúng tôi
              </p>
            </div>
            <div className="flex items-start">
              <div className="flex-shrink-0">
                <div className="w-6 h-6 bg-cyan-100 rounded-full flex items-center justify-center">
                  <span className="text-cyan-600 text-xs font-bold">2</span>
                </div>
              </div>
              <p className="ml-3 text-sm text-gray-700">
                Nhấp vào liên kết xác thực trong email
              </p>
            </div>
            <div className="flex items-start">
              <div className="flex-shrink-0">
                <div className="w-6 h-6 bg-cyan-100 rounded-full flex items-center justify-center">
                  <span className="text-cyan-600 text-xs font-bold">3</span>
                </div>
              </div>
              <p className="ml-3 text-sm text-gray-700">
                Quay lại đây và đăng nhập lại
              </p>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="space-y-3">
            <button
              onClick={handleCheckVerification}
              className="w-full flex justify-center py-3 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-gradient-to-r from-blue-600 via-cyan-500 to-indigo-600 hover:shadow-lg hover:scale-105 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-cyan-500 transition-all"
            >
              <svg
                className="w-5 h-5 mr-2"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"
                />
              </svg>
              Đã xác thực, đăng nhập ngay
            </button>

            <button
              onClick={handleResendVerification}
              disabled={!canResend}
              className={`w-full flex justify-center py-3 px-4 border text-sm font-medium rounded-md transition-all ${
                canResend
                  ? "border-cyan-300 text-cyan-700 bg-cyan-50 hover:bg-cyan-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-cyan-500"
                  : "border-gray-300 text-gray-400 bg-gray-50 cursor-not-allowed"
              }`}
            >
              {canResend ? (
                <>
                  <svg
                    className="w-5 h-5 mr-2"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"
                    />
                  </svg>
                  Gửi lại email xác thực
                </>
              ) : (
                `Gửi lại sau ${countdown}s`
              )}
            </button>
          </div>

          {/* Help Text */}
          <div className="mt-6 pt-6 border-t border-gray-200">
            <p className="text-xs text-center text-gray-500">
              Không nhận được email? Kiểm tra thư mục spam hoặc{" "}
              <button
                onClick={handleResendVerification}
                disabled={!canResend}
                className={`font-medium ${
                  canResend
                    ? "text-cyan-600 hover:text-cyan-700"
                    : "text-gray-400 cursor-not-allowed"
                }`}
              >
                gửi lại
              </button>
            </p>
          </div>

          {/* Back to Login */}
          <div className="mt-4 text-center">
            <Link
              href="/login"
              className="text-sm font-medium text-gray-600 hover:text-gray-900 transition-colors"
            >
              ← Quay lại trang đăng nhập
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

