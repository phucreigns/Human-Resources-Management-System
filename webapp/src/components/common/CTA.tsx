import Link from "next/link";

export const CTA = () => {
  return (
    <section className="py-20 bg-blue-600">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h2 className="text-3xl md:text-4xl font-bold text-white mb-6 drop-shadow-lg">
          Ready to Transform Your HR Operations?
        </h2>
        <p className="text-xl text-white/90 mb-8 max-w-2xl mx-auto drop-shadow-md">
          Join thousands of companies that trust HRMS to manage their workforce
          efficiently.
        </p>
        <div className="flex flex-col sm:flex-row gap-4 justify-center">
          <Link
            href="/signup"
            className="bg-white text-gray-900 px-8 py-4 rounded-lg text-lg font-semibold hover:bg-gray-100 hover:scale-105 transition-all shadow-2xl"
          >
            Start Free Trial
          </Link>
          <Link
            href="/contact"
            className="bg-transparent text-white px-8 py-4 rounded-lg text-lg font-semibold border-2 border-white hover:bg-white/10 transition-all"
          >
            Contact Sales
          </Link>
        </div>
      </div>
    </section>
  );
};

