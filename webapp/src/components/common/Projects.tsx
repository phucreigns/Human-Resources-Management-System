const projects = [
  {
    title: "HR Core Service",
    department: "HR Service",
    status: "Active",
    description:
      "Microservice for managing companies, departments, and positions. Provides RESTful APIs for organizational structure management with PostgreSQL database.",
    features: [
      "Company CRUD operations",
      "Department management by company",
      "Position management by department",
    ],
    image: (
      <div className="w-full h-48 bg-gradient-to-br from-blue-400 to-cyan-400 rounded-lg flex items-center justify-center">
        <svg
          className="w-24 h-24 text-white opacity-50"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={2}
            d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"
          />
        </svg>
      </div>
    ),
  },
  {
    title: "Time Tracking Service",
    department: "Time Tracking Service",
    status: "Active",
    description:
      "Microservice for attendance records and leave management. Handles employee attendance tracking, leave requests, and leave type configurations.",
    features: [
      "Attendance record management",
      "Leave request workflow with approval",
      "Leave type configuration",
    ],
    image: (
      <div className="w-full h-48 bg-gradient-to-br from-cyan-400 to-indigo-400 rounded-lg flex items-center justify-center">
        <svg
          className="w-24 h-24 text-white opacity-50"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={2}
            d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
          />
        </svg>
      </div>
    ),
  },
  {
    title: "File Service",
    department: "File Service",
    status: "Active",
    description:
      "Microservice for file and document management. Provides file upload, download, and document management capabilities with MongoDB storage.",
    features: [
      "Single and multiple file upload",
      "Document management by employee",
      "File retrieval and deletion",
    ],
    image: (
      <div className="w-full h-48 bg-gradient-to-br from-indigo-400 to-blue-400 rounded-lg flex items-center justify-center">
        <svg
          className="w-24 h-24 text-white opacity-50"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={2}
            d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
          />
        </svg>
      </div>
    ),
  },
  {
    title: "Notification Service",
    department: "Notification Service",
    status: "Active",
    description:
      "Microservice for sending email notifications. Handles email template management and automated email sending for HR operations.",
    features: [
      "Email sending functionality",
      "Email template management",
      "Automated notification system",
    ],
    image: (
      <div className="w-full h-48 bg-gradient-to-br from-blue-500 to-cyan-500 rounded-lg flex items-center justify-center">
        <svg
          className="w-24 h-24 text-white opacity-50"
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
    ),
  },
];

export const Projects = () => {
  return (
    <section id="projects" className="py-20 bg-gradient-to-b from-white via-gray-50 to-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-16">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
            System{" "}
            <span className="bg-gradient-to-r from-blue-600 via-cyan-500 to-indigo-600 bg-clip-text text-transparent">
              Modules
            </span>
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            Internal HR management modules designed and implemented for company
            workforce management.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {projects.map((project, index) => (
            <div
              key={index}
              className="bg-white border border-gray-200 rounded-xl overflow-hidden hover:shadow-2xl hover:shadow-blue-200/50 transition-all hover:scale-105 hover:border-blue-300 group"
            >
              {project.image}
              <div className="p-8">
                <div className="flex items-center justify-between mb-4">
                  <div>
                    <h3 className="text-xl font-semibold text-gray-900 mb-1">
                      {project.title}
                    </h3>
                    <p className="text-sm text-gray-600">
                      {project.department} • <span className="text-cyan-600 font-medium">{project.status}</span>
                    </p>
                  </div>
                </div>
                <p className="text-gray-600 mb-6">{project.description}</p>
                <div className="space-y-2">
                  <h4 className="text-sm font-semibold text-gray-900">
                    Key Features:
                  </h4>
                  <ul className="space-y-2">
                    {project.features.map((feature, idx) => (
                      <li
                        key={idx}
                        className="flex items-start text-sm text-gray-600"
                      >
                        <svg
                          className="w-5 h-5 mr-2 text-cyan-500 mt-0.5 flex-shrink-0"
                          fill="none"
                          stroke="currentColor"
                          viewBox="0 0 24 24"
                        >
                          <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth={2}
                            d="M5 13l4 4L19 7"
                          />
                        </svg>
                        {feature}
                      </li>
                    ))}
                  </ul>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

