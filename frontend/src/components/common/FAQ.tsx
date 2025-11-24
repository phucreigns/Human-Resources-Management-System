"use client";

import { useState } from "react";

interface FAQItem {
  question: string;
  answer: string;
}

export const FAQ = () => {
  const [openIndex, setOpenIndex] = useState<number | null>(null);

  const faqs: FAQItem[] = [
    {
      question: "HRMS là gì và dành cho ai?",
      answer: "HRMS (Human Resource Management System) là hệ thống quản lý nhân sự toàn diện, được thiết kế cho các doanh nghiệp từ nhỏ đến lớn. Hệ thống phù hợp với các công ty cần quản lý nhân viên, chấm công, tính lương, quản lý phép năm và báo cáo nhân sự một cách chuyên nghiệp và hiệu quả.",
    },
    {
      question: "HRMS có những tính năng chính nào?",
      answer: "HRMS bao gồm các tính năng chính: Quản lý thông tin nhân viên và tổ chức (công ty, phòng ban, chức vụ), Theo dõi chấm công và thời gian làm việc, Quản lý phép năm và đơn xin nghỉ, Tính lương và quản lý bảng lương, Báo cáo và phân tích nhân sự, Quản lý quyền truy cập theo vai trò (RBAC). Tất cả được tích hợp trong một nền tảng duy nhất.",
    },
    {
      question: "HRMS có an toàn và bảo mật không?",
      answer: "Có, HRMS được xây dựng với các tiêu chuẩn bảo mật cao. Hệ thống sử dụng Auth0 cho xác thực, JWT tokens cho session management, mã hóa dữ liệu trong quá trình truyền, và quản lý quyền truy cập theo vai trò (RBAC). Dữ liệu được lưu trữ an toàn trong PostgreSQL với các biện pháp backup định kỳ.",
    },
    {
      question: "Làm thế nào để bắt đầu sử dụng HRMS?",
      answer: "Để bắt đầu sử dụng HRMS, bạn chỉ cần đăng ký tài khoản miễn phí bằng cách click vào nút 'Login / Register' ở góc trên bên phải. Sau khi đăng ký và xác thực email, bạn sẽ được cấp quyền truy cập vào hệ thống. Nếu bạn là quản trị viên, bạn có thể bắt đầu tạo công ty, phòng ban và thêm nhân viên ngay lập tức.",
    },
    {
      question: "HRMS có hỗ trợ đa công ty không?",
      answer: "Có, HRMS được thiết kế để hỗ trợ quản lý nhiều công ty trong cùng một hệ thống. Mỗi công ty có thể có nhiều phòng ban, mỗi phòng ban có nhiều chức vụ khác nhau. Hệ thống cho phép quản trị viên quản lý toàn bộ cấu trúc tổ chức một cách linh hoạt và dễ dàng.",
    },
  ];

  const toggleFAQ = (index: number) => {
    setOpenIndex(openIndex === index ? null : index);
  };

  return (
    <section id="faq" className="py-20 bg-gradient-to-b from-white via-gray-50 to-white">
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
            Câu hỏi{" "}
            <span className="bg-gradient-to-r from-blue-600 via-cyan-500 to-indigo-600 bg-clip-text text-transparent">
              Thường Gặp
            </span>
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            Tất cả những gì bạn cần biết về HRMS và cách nó có thể biến đổi quy trình quản lý nhân sự của bạn
          </p>
        </div>

        <div className="space-y-4">
          {faqs.map((faq, index) => (
            <div
              key={index}
              className="bg-white border border-gray-200 rounded-lg overflow-hidden hover:shadow-lg transition-shadow"
            >
              <button
                onClick={() => toggleFAQ(index)}
                className="w-full px-6 py-4 text-left flex items-center justify-between focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-inset"
              >
                <span className="font-semibold text-gray-900 pr-4">{faq.question}</span>
                <svg
                  className={`w-5 h-5 text-gray-500 flex-shrink-0 transition-transform ${
                    openIndex === index ? "transform rotate-180" : ""
                  }`}
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M19 9l-7 7-7-7"
                  />
                </svg>
              </button>
              {openIndex === index && (
                <div className="px-6 pb-4">
                  <div className="pt-2 border-t border-gray-200">
                    <p className="text-gray-700 leading-relaxed">{faq.answer}</p>
                  </div>
                </div>
              )}
            </div>
          ))}
        </div>

        <div className="mt-12 text-center">
          <p className="text-gray-600 mb-4">Vẫn còn thắc mắc?</p>
          <a
            href="/contact"
            className="inline-flex items-center px-6 py-3 bg-gradient-to-r from-blue-600 to-cyan-500 text-white rounded-lg font-semibold hover:shadow-lg transition-all hover:scale-105"
          >
            Liên hệ với chúng tôi
            <svg className="w-5 h-5 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
            </svg>
          </a>
        </div>
      </div>
    </section>
  );
};

