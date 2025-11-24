"use client";

export const Introduction = () => {
  const strengths = [
    {
      icon: (
        <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
        </svg>
      ),
      title: "Hiệu suất cao",
      description: "Hệ thống được xây dựng với kiến trúc microservices, đảm bảo hiệu suất và khả năng mở rộng tối ưu.",
    },
    {
      icon: (
        <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
        </svg>
      ),
      title: "Bảo mật tối đa",
      description: "Xác thực qua Auth0, mã hóa dữ liệu và quản lý quyền truy cập theo vai trò (RBAC) đảm bảo an toàn thông tin.",
    },
    {
      icon: (
        <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 5a1 1 0 011-1h14a1 1 0 011 1v2a1 1 0 01-1 1H5a1 1 0 01-1-1V5zM4 13a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H5a1 1 0 01-1-1v-6zM16 13a1 1 0 011-1h2a1 1 0 011 1v6a1 1 0 01-1 1h-2a1 1 0 01-1-1v-6z" />
        </svg>
      ),
      title: "Giao diện hiện đại",
      description: "UI/UX được thiết kế với Next.js và Tailwind CSS, mang lại trải nghiệm người dùng mượt mà và trực quan.",
    },
    {
      icon: (
        <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
        </svg>
      ),
      title: "Đáng tin cậy",
      description: "Kiến trúc microservices độc lập, dễ bảo trì và nâng cấp, đảm bảo hệ thống hoạt động ổn định 24/7.",
    },
  ];

  return (
    <section id="introduction" className="py-20 bg-gradient-to-b from-gray-50 via-white to-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
            Giới thiệu về{" "}
            <span className="bg-gradient-to-r from-blue-600 via-cyan-500 to-indigo-600 bg-clip-text text-transparent">
              HRMS
            </span>
          </h2>
          <p className="text-xl text-gray-600 max-w-3xl mx-auto">
            HRMS (Human Resource Management System) là hệ thống quản lý nhân sự toàn diện, 
            được xây dựng với công nghệ hiện đại để giúp doanh nghiệp quản lý nhân viên một cách 
            hiệu quả và chuyên nghiệp.
          </p>
        </div>

        <div className="mb-12 bg-white rounded-2xl shadow-xl p-8 md:p-12 border border-gray-200">
          <h3 className="text-2xl font-bold text-gray-900 mb-6 text-center">
            Hệ thống quản lý nhân sự thông minh
          </h3>
          <div className="grid md:grid-cols-2 gap-8 text-gray-700">
            <div>
              <p className="mb-4 text-lg leading-relaxed">
                HRMS cung cấp giải pháp quản lý nhân sự toàn diện, từ quản lý thông tin nhân viên, 
                theo dõi chấm công, quản lý phép năm, đến tính lương và báo cáo. Hệ thống được 
                thiết kế để đáp ứng nhu cầu của các doanh nghiệp từ nhỏ đến lớn.
              </p>
              <p className="text-lg leading-relaxed">
                Với giao diện trực quan và dễ sử dụng, HRMS giúp giảm thiểu thời gian xử lý các 
                công việc hành chính, cho phép bộ phận HR tập trung vào các chiến lược phát triển 
                nhân sự quan trọng hơn.
              </p>
            </div>
            <div>
              <p className="mb-4 text-lg leading-relaxed">
                Hệ thống hỗ trợ quản lý đa công ty, đa phòng ban với cấu trúc phân quyền linh hoạt. 
                Mỗi nhân viên có thể truy cập thông tin cá nhân, xem lịch làm việc, đăng ký phép, 
                và theo dõi lương của mình một cách dễ dàng.
              </p>
              <p className="text-lg leading-relaxed">
                HRMS không chỉ là công cụ quản lý mà còn là nền tảng hỗ trợ ra quyết định với các 
                báo cáo và phân tích chi tiết về nhân sự, giúp lãnh đạo có cái nhìn tổng quan về 
                tình hình nhân lực của công ty.
              </p>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {strengths.map((strength, index) => (
            <div
              key={index}
              className="bg-white border border-gray-200 rounded-xl p-6 hover:shadow-xl hover:shadow-blue-200/50 transition-all hover:scale-105 hover:border-blue-300"
            >
              <div className="w-16 h-16 bg-gradient-to-br from-blue-500 to-cyan-500 rounded-lg flex items-center justify-center text-white mb-4 shadow-lg">
                {strength.icon}
              </div>
              <h4 className="text-xl font-semibold text-gray-900 mb-2">
                {strength.title}
              </h4>
              <p className="text-gray-600">{strength.description}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

