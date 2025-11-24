import Link from "next/link";

export const Hero = () => {
  return (
    <section className="relative min-h-screen bg-gradient-to-b from-white via-sky-50/500 to-sky-300">

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-32 pb-20">
        <div className="text-center max-w-4xl mx-auto">
          {/* Main Headline */}
          <h1 className="text-5xl md:text-6xl lg:text-7xl font-bold text-gray-900 mb-6 leading-tight">
            Streamline Your{" "}
            <span className="text-blue-600">
              HR Operations
            </span>{" "}
            with Modern Technology
          </h1>
          
          {/* Sub-headline */}
          <p className="text-xl md:text-2xl text-gray-700 mb-12 max-w-3xl mx-auto leading-relaxed">
            Comprehensive Human Resource Management System designed to
            simplify employee management, payroll, attendance tracking, and
            more. All in one powerful platform for internal company use.
          </p>
        </div>

        {/* Embedded Application Demo */}
        <div className="mt-16 max-w-5xl mx-auto">
          <div className="relative bg-white rounded-2xl shadow-2xl overflow-hidden border border-gray-200">
            {/* macOS window chrome */}
            <div className="bg-gray-100 px-4 py-3 flex items-center space-x-2 border-b border-gray-200">
              <div className="flex space-x-2">
                <div className="w-3 h-3 rounded-full bg-red-500"></div>
                <div className="w-3 h-3 rounded-full bg-yellow-500"></div>
                <div className="w-3 h-3 rounded-full bg-green-500"></div>
              </div>
            </div>
            
            {/* Application content */}
            <div className="relative bg-gradient-to-br from-orange-200 via-yellow-200 via-purple-200 to-yellow-200 p-8 min-h-[500px]">
              <div className="bg-white/90 backdrop-blur-sm rounded-xl p-6 shadow-lg border border-white/50">
                <div className="flex items-center justify-between mb-4">
                  <div className="flex items-center space-x-3">
                    <div className="w-12 h-12 bg-gradient-to-br from-blue-500 to-cyan-500 rounded-full flex items-center justify-center">
                      <svg
                        className="w-6 h-6 text-white"
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
                    <div>
                      <div className="font-semibold text-gray-900">
                        Employee Management
                      </div>
                      <div className="text-sm text-gray-600">
                        1,234 Active Employees
                      </div>
                    </div>
                  </div>
                </div>
                <div className="grid grid-cols-3 gap-4 pt-4 border-t border-gray-200">
                  <div className="text-center">
                    <div className="text-2xl font-bold bg-gradient-to-r from-blue-600 to-cyan-500 bg-clip-text text-transparent">98%</div>
                    <div className="text-xs text-gray-600">Satisfaction</div>
                  </div>
                  <div className="text-center">
                    <div className="text-2xl font-bold bg-gradient-to-r from-cyan-500 to-blue-500 bg-clip-text text-transparent">100%</div>
                    <div className="text-xs text-gray-600">Internal</div>
                  </div>
                  <div className="text-center">
                    <div className="text-2xl font-bold bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">99.9%</div>
                    <div className="text-xs text-gray-600">Uptime</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

