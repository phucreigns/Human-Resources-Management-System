"use client";

import { useAuth } from "@/context/AuthContext";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Header } from "@/components/common/Header";
import { Footer } from "@/components/common/Footer";
import { UserManagement } from "@/features/users";
import { CompanyManagement, DepartmentManagement, PositionManagement } from "@/features/hr";
import { LeaveTypeManagement, LeaveRequestManagement } from "@/features/timetracking";

type TabType = "users" | "companies" | "departments" | "positions" | "leave-types" | "leave-requests";

export default function AdminPage() {
  const { isAuthenticated, isLoading, isAdmin } = useAuth();
  const router = useRouter();
  const [activeTab, setActiveTab] = useState<TabType>("users");

  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      router.push("/");
    } else if (!isLoading && !isAdmin) {
      router.push("/");
    }
  }, [isAuthenticated, isLoading, isAdmin, router]);

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gradient-to-b from-sky-200 via-sky-100 to-white">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-sky-600"></div>
      </div>
    );
  }

  if (!isAuthenticated || !isAdmin) {
    return null;
  }

  const tabs = [
    { id: "users" as TabType, label: "User Management" },
    { id: "companies" as TabType, label: "Companies" },
    { id: "departments" as TabType, label: "Departments" },
    { id: "positions" as TabType, label: "Positions" },
    { id: "leave-types" as TabType, label: "Leave Types" },
    { id: "leave-requests" as TabType, label: "Leave Requests" },
  ];

  return (
    <div className="min-h-screen bg-gradient-to-b from-sky-200 via-sky-100 to-white">
      <Header />
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="bg-white rounded-2xl shadow-xl border border-gray-200 overflow-hidden">
          <div className="px-8 py-6 border-b border-gray-200">
            <h1 className="text-3xl font-bold text-gray-900 mb-2">Admin Dashboard</h1>
            <p className="text-gray-600">Manage all aspects of the HRMS system</p>
          </div>

          {/* Tabs */}
          <div className="border-b border-gray-200">
            <nav className="flex space-x-8 px-8" aria-label="Tabs">
              {tabs.map((tab) => (
                <button
                  key={tab.id}
                  onClick={() => setActiveTab(tab.id)}
                  className={`py-4 px-1 border-b-2 font-medium text-sm ${
                    activeTab === tab.id
                      ? "border-sky-500 text-sky-600"
                      : "border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300"
                  }`}
                >
                  {tab.label}
                </button>
              ))}
            </nav>
          </div>

          {/* Tab Content */}
          <div className="p-8">
            {activeTab === "users" && <UserManagement />}
            {activeTab === "companies" && <CompanyManagement />}
            {activeTab === "departments" && <DepartmentManagement />}
            {activeTab === "positions" && <PositionManagement />}
            {activeTab === "leave-types" && <LeaveTypeManagement />}
            {activeTab === "leave-requests" && <LeaveRequestManagement />}
          </div>
        </div>
      </main>
      <Footer />
    </div>
  );
}


