"use client";

import React, { createContext, useContext, useState, useEffect } from "react";

interface User {
  email: string;
  name: string;
  picture?: string;
  roles?: string[];
  phoneNumber?: string;
  position?: string;
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  isLoading: boolean;
  isAuthenticated: boolean;
  isAdmin: boolean;
  login: () => void;
  logout: () => void;
  setAuthData: (token: string, user: User) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const decodeToken = (token: string): any => {
    try {
      const tokenParts = token.split(".");
      if (tokenParts.length === 3) {
        return JSON.parse(atob(tokenParts[1]));
      }
    } catch (error) {
      console.error("Error decoding token:", error);
    }
    return null;
  };

  useEffect(() => {
    // Check for stored auth data on mount
    // Use sessionStorage instead of localStorage so each tab has its own session
    const storedToken = sessionStorage.getItem("accessToken");
    const storedUser = sessionStorage.getItem("user");

    if (storedToken && storedUser) {
      setToken(storedToken);
      const userData = JSON.parse(storedUser);
      
      // Decode token to get roles and additional info
      const payload = decodeToken(storedToken);
      if (payload) {
        // Get roles from JWT (Auth0 stores roles in custom claim)
        const roles = payload["https://xuanphuchrms.com/roles"] || payload.roles || [];
        userData.roles = Array.isArray(roles) ? roles : [];
      }
      
      setUser(userData);
    }
    setIsLoading(false);
  }, []);

  const login = () => {
    // Clear any existing session before login to ensure fresh login
    sessionStorage.clear();
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("user");
    
    const redirectUri = "http://localhost:3000";
    // Add prompt=login to force Auth0 to show login screen (not auto-login)
    // Add prompt=select_account to allow user to choose different account
    const auth0Url = `http://dev-kvwfevkuw0dqnvc2.us.auth0.com/authorize?client_id=JQGQbJkzbYwd16ykAGBJphm4UViy1H4h&response_type=code&redirect_uri=${encodeURIComponent(
      redirectUri
    )}&scope=openid%20email%20profile%20offline_access&audience=https://xuanphuchrms.com/&prompt=login%20select_account`;
    window.location.href = auth0Url;
  };

  const logout = () => {
    // Clear both sessionStorage and localStorage
    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("refreshToken");
    sessionStorage.removeItem("user");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("user");
    setToken(null);
    setUser(null);
    window.location.href = "/";
  };

  const setAuthData = (newToken: string, newUser: User) => {
    // Decode token to get roles
    const payload = decodeToken(newToken);
    if (payload) {
      const roles = payload["https://xuanphuchrms.com/roles"] || payload.roles || [];
      newUser.roles = Array.isArray(roles) ? roles : [];
      // Debug: log roles to console
      console.log("Decoded roles from JWT:", newUser.roles);
      console.log("Full JWT payload:", payload);
    }
    
    setToken(newToken);
    setUser(newUser);
    // Use sessionStorage instead of localStorage so each tab has its own session
    sessionStorage.setItem("accessToken", newToken);
    sessionStorage.setItem("user", JSON.stringify(newUser));
  };

  const isAdmin = user?.roles?.some(role => {
    const roleUpper = role.toUpperCase();
    return roleUpper === "ADMIN" || roleUpper === "ROLE_ADMIN";
  }) || false;

  // Debug: log isAdmin status
  useEffect(() => {
    if (user) {
      console.log("User roles:", user.roles);
      console.log("Is Admin:", isAdmin);
    }
  }, [user, isAdmin]);

  const value: AuthContextType = {
    user,
    token,
    isLoading,
    isAuthenticated: !!token && !!user,
    isAdmin,
    login,
    logout,
    setAuthData,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

