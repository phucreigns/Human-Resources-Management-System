"use client";

import { useEffect, useState } from "react";
import { useMyProfile, UserUpdateRequest } from "../hooks/useUsers";

interface UserProfileProps {
  isEditable?: boolean;
}

export const UserProfile: React.FC<UserProfileProps> = ({ isEditable = true }) => {
  const { user, loading, fetchMyProfile, updateMyProfile } = useMyProfile();
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState<UserUpdateRequest>({
    fullName: "",
    email: "",
    phoneNumber: "",
    avatarUrl: "",
    address: "",
  });

  useEffect(() => {
    fetchMyProfile();
  }, []);

  useEffect(() => {
    if (user) {
      setFormData({
        fullName: user.fullName || "",
        email: user.email || "",
        phoneNumber: user.phoneNumber || "",
        avatarUrl: user.avatarUrl || "",
        address: user.address || "",
      });
    }
  }, [user]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await updateMyProfile(formData);
      // Fetch updated profile to ensure we have the latest data
      await fetchMyProfile();
      setIsEditing(false);
    } catch (error) {
      // Error is handled by the hook
    }
  };

  const handleCancel = () => {
    if (user) {
      setFormData({
        fullName: user.fullName || "",
        email: user.email || "",
        phoneNumber: user.phoneNumber || "",
        avatarUrl: user.avatarUrl || "",
        address: user.address || "",
      });
    }
    setIsEditing(false);
  };

  if (loading && !user) {
    return (
      <div className="flex items-center justify-center p-8">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-sky-600"></div>
      </div>
    );
  }

  if (!user) {
    return (
      <div className="p-8 text-center text-gray-500">
        No user data available
      </div>
    );
  }

  return (
    <div className="bg-white rounded-2xl shadow-xl border border-gray-200 overflow-hidden">
      {/* Profile Header */}
      <div className="bg-gradient-to-r from-sky-500 to-sky-600 px-8 py-12">
        <div className="flex items-center space-x-6">
          {user.avatarUrl || (isEditing && formData.avatarUrl) ? (
            <img
              src={isEditing ? formData.avatarUrl : user.avatarUrl}
              alt={user.fullName}
              className="w-24 h-24 rounded-full border-4 border-white shadow-lg object-cover"
            />
          ) : (
            <div className="w-24 h-24 bg-white/20 backdrop-blur-sm rounded-full flex items-center justify-center border-4 border-white shadow-lg">
              <svg
                className="w-12 h-12 text-white"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                />
              </svg>
            </div>
          )}
          <div className="flex-1">
            <h1 className="text-3xl font-bold text-white mb-2">
              {isEditing ? formData.fullName : user.fullName}
            </h1>
            <p className="text-sky-100 text-lg">{user.email}</p>
            {user.employeeCode && (
              <p className="text-sky-100 text-sm mt-1">Employee Code: {user.employeeCode}</p>
            )}
          </div>
          {isEditable && !isEditing && (
            <button
              onClick={() => setIsEditing(true)}
              className="px-4 py-2 bg-white text-sky-600 rounded-lg font-medium hover:bg-sky-50 transition-colors"
            >
              Edit Profile
            </button>
          )}
        </div>
      </div>

      {/* Profile Content */}
      <div className="p-8">
        <form onSubmit={handleSubmit}>
          <h2 className="text-2xl font-bold text-gray-900 mb-6">Personal Information</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* Full Name */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Full Name <span className="text-red-500">*</span>
              </label>
              {isEditing ? (
                <input
                  type="text"
                  value={formData.fullName}
                  onChange={(e) => setFormData({ ...formData, fullName: e.target.value })}
                  required
                  maxLength={100}
                  className="w-full border border-gray-300 rounded-lg px-4 py-3 focus:ring-2 focus:ring-sky-500 focus:border-transparent"
                />
              ) : (
                <div className="bg-gray-50 border border-gray-200 rounded-lg px-4 py-3 text-gray-900">
                  {user.fullName || "N/A"}
                </div>
              )}
            </div>

            {/* Email */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Email <span className="text-red-500">*</span>
              </label>
              {isEditing ? (
                <input
                  type="email"
                  value={formData.email || ""}
                  onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                  required
                  maxLength={255}
                  className="w-full border border-gray-300 rounded-lg px-4 py-3 focus:ring-2 focus:ring-sky-500 focus:border-transparent"
                  placeholder="Enter email address"
                />
              ) : (
                <div className="bg-gray-50 border border-gray-200 rounded-lg px-4 py-3 text-gray-900">
                  {user.email || "N/A"}
                </div>
              )}
            </div>

            {/* Phone Number */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Phone Number</label>
              {isEditing ? (
                <input
                  type="tel"
                  value={formData.phoneNumber}
                  onChange={(e) => setFormData({ ...formData, phoneNumber: e.target.value })}
                  maxLength={30}
                  className="w-full border border-gray-300 rounded-lg px-4 py-3 focus:ring-2 focus:ring-sky-500 focus:border-transparent"
                />
              ) : (
                <div className="bg-gray-50 border border-gray-200 rounded-lg px-4 py-3 text-gray-900">
                  {user.phoneNumber || "N/A"}
                </div>
              )}
            </div>

            {/* Avatar URL */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Avatar URL</label>
              {isEditing ? (
                <input
                  type="url"
                  value={formData.avatarUrl}
                  onChange={(e) => setFormData({ ...formData, avatarUrl: e.target.value })}
                  placeholder="https://example.com/avatar.jpg"
                  maxLength={255}
                  className="w-full border border-gray-300 rounded-lg px-4 py-3 focus:ring-2 focus:ring-sky-500 focus:border-transparent"
                />
              ) : (
                <div className="bg-gray-50 border border-gray-200 rounded-lg px-4 py-3 text-gray-900">
                  {user.avatarUrl || "N/A"}
                </div>
              )}
            </div>

            {/* Address */}
            <div className="md:col-span-2">
              <label className="block text-sm font-medium text-gray-700 mb-2">Address</label>
              {isEditing ? (
                <textarea
                  value={formData.address}
                  onChange={(e) => setFormData({ ...formData, address: e.target.value })}
                  rows={3}
                  maxLength={500}
                  className="w-full border border-gray-300 rounded-lg px-4 py-3 focus:ring-2 focus:ring-sky-500 focus:border-transparent"
                  placeholder="Enter address (max 500 characters)"
                />
              ) : (
                <div className="bg-gray-50 border border-gray-200 rounded-lg px-4 py-3 text-gray-900">
                  {user.address || "N/A"}
                </div>
              )}
            </div>

            {/* Status */}
            {user.status && (
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Status</label>
                <div className="bg-gray-50 border border-gray-200 rounded-lg px-4 py-3">
                  <span className={`inline-flex items-center px-3 py-1 rounded-full text-sm font-medium ${
                    user.status === "ACTIVE" 
                      ? "bg-green-100 text-green-800" 
                      : "bg-gray-100 text-gray-800"
                  }`}>
                    {user.status}
                  </span>
                </div>
              </div>
            )}
          </div>

          {/* Action Buttons */}
          {isEditing && (
            <div className="mt-6 flex justify-end space-x-4">
              <button
                type="button"
                onClick={handleCancel}
                className="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors"
              >
                Cancel
              </button>
              <button
                type="submit"
                disabled={loading}
                className="px-6 py-2 bg-sky-600 text-white rounded-lg font-medium hover:bg-sky-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {loading ? "Saving..." : "Save Changes"}
              </button>
            </div>
          )}
        </form>
      </div>
    </div>
  );
};


