"use client";

export const Technology = () => {
  const technologies = [
    {
      category: "Frontend",
      items: [
        { name: "Next.js 14", description: "React framework với App Router, SSR và SSG" },
        { name: "React 18", description: "Thư viện UI component hiện đại" },
        { name: "TypeScript", description: "Type-safe JavaScript cho code quality" },
        { name: "Tailwind CSS", description: "Utility-first CSS framework" },
        { name: "Redux Toolkit", description: "State management với Redux" },
      ],
      color: "from-blue-500 to-cyan-500",
    },
    {
      category: "Backend",
      items: [
        { name: "Spring Boot 3", description: "Java framework cho microservices" },
        { name: "Spring Security", description: "Authentication & Authorization" },
        { name: "Spring Data JPA", description: "Database ORM và persistence" },
        { name: "PostgreSQL", description: "Relational database management" },
        { name: "Maven", description: "Dependency management và build tool" },
      ],
      color: "from-green-500 to-emerald-500",
    },
    {
      category: "Authentication",
      items: [
        { name: "Auth0", description: "Identity platform và authentication" },
        { name: "JWT", description: "JSON Web Tokens cho stateless auth" },
        { name: "OAuth 2.0", description: "Authorization framework" },
      ],
      color: "from-purple-500 to-pink-500",
    },
    {
      category: "Architecture",
      items: [
        { name: "Microservices", description: "Kiến trúc phân tán, độc lập" },
        { name: "RESTful API", description: "API design pattern" },
        { name: "Docker", description: "Containerization cho deployment" },
        { name: "Feign Client", description: "Service-to-service communication" },
      ],
      color: "from-orange-500 to-red-500",
    },
  ];

  return (
    <section id="technology" className="py-20 bg-gradient-to-b from-gray-50 via-white to-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
            Công nghệ{" "}
            <span className="bg-gradient-to-r from-blue-600 via-cyan-500 to-indigo-600 bg-clip-text text-transparent">
              Sử dụng
            </span>
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            HRMS được xây dựng với các công nghệ hiện đại và best practices
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {technologies.map((tech, index) => (
            <div
              key={index}
              className="bg-white border border-gray-200 rounded-xl p-6 hover:shadow-xl transition-all"
            >
              <div className={`w-16 h-16 bg-gradient-to-br ${tech.color} rounded-lg flex items-center justify-center text-white mb-4 shadow-lg`}>
                <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4" />
                </svg>
              </div>
              <h3 className="text-2xl font-bold text-gray-900 mb-4">{tech.category}</h3>
              <div className="space-y-3">
                {tech.items.map((item, itemIndex) => (
                  <div key={itemIndex} className="border-l-4 border-blue-500 pl-4">
                    <h4 className="font-semibold text-gray-900 mb-1">{item.name}</h4>
                    <p className="text-sm text-gray-600">{item.description}</p>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>

        <div className="mt-12 bg-gradient-to-r from-blue-600 via-cyan-500 to-indigo-600 rounded-2xl p-8 text-white">
          <h3 className="text-2xl font-bold mb-4 text-center">Kiến trúc hệ thống</h3>
          <div className="grid md:grid-cols-3 gap-6 text-center">
            <div>
              <div className="text-4xl font-bold mb-2">6+</div>
              <div className="text-blue-100">Microservices</div>
            </div>
            <div>
              <div className="text-4xl font-bold mb-2">100%</div>
              <div className="text-blue-100">Type Safety</div>
            </div>
            <div>
              <div className="text-4xl font-bold mb-2">24/7</div>
              <div className="text-blue-100">Uptime</div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

