"use client";

export const Reviews = () => {
  const reviews = [
    {
      name: "Nguyễn Văn A",
      position: "Giám đốc Nhân sự",
      company: "Công ty ABC",
      rating: 5,
      comment: "HRMS đã thay đổi hoàn toàn cách chúng tôi quản lý nhân sự. Giao diện dễ sử dụng, tính năng đầy đủ và hỗ trợ khách hàng rất tốt. Tôi đặc biệt ấn tượng với tính năng báo cáo tự động.",
      avatar: "👨‍💼",
    },
    {
      name: "Trần Thị B",
      position: "Chuyên viên HR",
      company: "Công ty XYZ",
      rating: 5,
      comment: "Hệ thống giúp tôi tiết kiệm rất nhiều thời gian trong việc xử lý chấm công và tính lương. Tính năng quản lý phép năm rất tiện lợi, nhân viên có thể tự đăng ký và theo dõi trạng thái.",
      avatar: "👩‍💼",
    },
    {
      name: "Lê Văn C",
      position: "CEO",
      company: "Startup Tech",
      rating: 5,
      comment: "Là một startup, chúng tôi cần một hệ thống HR linh hoạt và dễ mở rộng. HRMS đáp ứng đầy đủ nhu cầu của chúng tôi. Giá cả hợp lý và tính năng phong phú.",
      avatar: "👨‍💻",
    },
    {
      name: "Phạm Thị D",
      position: "Nhân viên",
      company: "Công ty DEF",
      rating: 5,
      comment: "Tôi rất thích ứng dụng này! Có thể xem lịch làm việc, đăng ký phép, và xem thông tin lương một cách dễ dàng. Giao diện đẹp và thân thiện với người dùng.",
      avatar: "👩‍💻",
    },
    {
      name: "Hoàng Văn E",
      position: "Trưởng phòng IT",
      company: "Công ty GHI",
      rating: 5,
      comment: "Từ góc độ kỹ thuật, HRMS có kiến trúc tốt, API rõ ràng và dễ tích hợp. Bảo mật được đảm bảo tốt với Auth0. Hệ thống ổn định và hiếm khi gặp sự cố.",
      avatar: "👨‍🔧",
    },
  ];

  const renderStars = (rating: number) => {
    return Array.from({ length: 5 }).map((_, index) => (
      <svg
        key={index}
        className={`w-5 h-5 ${index < rating ? "text-yellow-400" : "text-gray-300"}`}
        fill="currentColor"
        viewBox="0 0 20 20"
      >
        <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
      </svg>
    ));
  };

  return (
    <section id="reviews" className="py-20 bg-gradient-to-b from-white via-blue-50 to-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
            Đánh giá từ{" "}
            <span className="bg-gradient-to-r from-blue-600 via-cyan-500 to-indigo-600 bg-clip-text text-transparent">
              Người dùng
            </span>
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            Những phản hồi chân thực từ khách hàng đã sử dụng HRMS
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {reviews.map((review, index) => (
            <div
              key={index}
              className="bg-white border border-gray-200 rounded-xl p-6 hover:shadow-xl hover:shadow-blue-200/50 transition-all hover:scale-105"
            >
              <div className="flex items-center mb-4">
                <div className="w-12 h-12 bg-gradient-to-br from-blue-500 to-cyan-500 rounded-full flex items-center justify-center text-2xl mr-4">
                  {review.avatar}
                </div>
                <div className="flex-1">
                  <h4 className="font-semibold text-gray-900">{review.name}</h4>
                  <p className="text-sm text-gray-600">{review.position}</p>
                  <p className="text-xs text-gray-500">{review.company}</p>
                </div>
              </div>
              <div className="flex items-center mb-3">
                {renderStars(review.rating)}
              </div>
              <p className="text-gray-700 leading-relaxed italic">
                "{review.comment}"
              </p>
            </div>
          ))}
        </div>

        <div className="mt-12 text-center">
          <div className="inline-flex items-center space-x-2 bg-white rounded-full px-6 py-3 shadow-lg border border-gray-200">
            <div className="flex items-center">
              {renderStars(5)}
            </div>
            <span className="text-gray-700 font-semibold">4.9/5.0</span>
            <span className="text-gray-500">từ 50+ đánh giá</span>
          </div>
        </div>
      </div>
    </section>
  );
};

