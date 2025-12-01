import React from "react";

interface LogoProps {
  className?: string;
  size?: "sm" | "md" | "lg";
}

export const Logo: React.FC<LogoProps> = ({ className = "", size = "md" }) => {
  const sizeClasses = {
    sm: "w-8 h-8",
    md: "w-10 h-10",
    lg: "w-16 h-16",
  };

  const textSizes = {
    sm: "text-xs",
    md: "text-sm",
    lg: "text-lg",
  };

  return (
    <div className={`${sizeClasses[size]} ${className} relative`}>
      <svg
        viewBox="0 0 120 120"
        className="w-full h-full"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <defs>
          <linearGradient id="chipGradient" x1="0%" y1="0%" x2="0%" y2="100%">
            <stop offset="0%" stopColor="#00f0ff" stopOpacity="1" />
            <stop offset="50%" stopColor="#a855f7" stopOpacity="1" />
            <stop offset="100%" stopColor="#ec4899" stopOpacity="1" />
          </linearGradient>
        </defs>

        {/* Circuit lines - Top */}
        <path
          d="M 20 20 L 20 10 M 30 20 L 30 10 M 40 20 L 40 10 M 50 20 L 50 10"
          stroke="url(#chipGradient)"
          strokeWidth="2"
          strokeLinecap="round"
        />
        <circle cx="20" cy="10" r="3" fill="url(#chipGradient)" />
        <circle cx="30" cy="10" r="3" fill="url(#chipGradient)" />
        <circle cx="40" cy="10" r="3" fill="url(#chipGradient)" />
        <circle cx="50" cy="10" r="3" fill="url(#chipGradient)" />

        {/* Circuit lines - Right */}
        <path
          d="M 100 20 L 110 20 M 100 30 L 110 30 M 100 40 L 110 40 M 100 50 L 110 50"
          stroke="url(#chipGradient)"
          strokeWidth="2"
          strokeLinecap="round"
        />
        <circle cx="110" cy="20" r="3" fill="url(#chipGradient)" />
        <circle cx="110" cy="30" r="3" fill="url(#chipGradient)" />
        <circle cx="110" cy="40" r="3" fill="url(#chipGradient)" />
        <circle cx="110" cy="50" r="3" fill="url(#chipGradient)" />

        {/* Circuit lines - Bottom */}
        <path
          d="M 20 100 L 20 110 M 30 100 L 30 110 M 40 100 L 40 110 M 50 100 L 50 110"
          stroke="url(#chipGradient)"
          strokeWidth="2"
          strokeLinecap="round"
        />
        <circle cx="20" cy="110" r="3" fill="url(#chipGradient)" />
        <circle cx="30" cy="110" r="3" fill="url(#chipGradient)" />
        <circle cx="40" cy="110" r="3" fill="url(#chipGradient)" />
        <circle cx="50" cy="110" r="3" fill="url(#chipGradient)" />

        {/* Circuit lines - Left */}
        <path
          d="M 20 20 L 10 20 M 20 30 L 10 30 M 20 40 L 10 40 M 20 50 L 10 50"
          stroke="url(#chipGradient)"
          strokeWidth="2"
          strokeLinecap="round"
        />
        <circle cx="10" cy="20" r="3" fill="url(#chipGradient)" />
        <circle cx="10" cy="30" r="3" fill="url(#chipGradient)" />
        <circle cx="10" cy="40" r="3" fill="url(#chipGradient)" />
        <circle cx="10" cy="50" r="3" fill="url(#chipGradient)" />

        {/* Additional circuit branches */}
        <path
          d="M 70 20 L 70 10 M 80 20 L 80 10 M 90 20 L 90 10"
          stroke="url(#chipGradient)"
          strokeWidth="1.5"
          strokeLinecap="round"
        />
        <circle cx="70" cy="10" r="2" fill="url(#chipGradient)" />
        <circle cx="80" cy="10" r="2" fill="url(#chipGradient)" />
        <circle cx="90" cy="10" r="2" fill="url(#chipGradient)" />

        <path
          d="M 100 70 L 110 70 M 100 80 L 110 80 M 100 90 L 110 90"
          stroke="url(#chipGradient)"
          strokeWidth="1.5"
          strokeLinecap="round"
        />
        <circle cx="110" cy="70" r="2" fill="url(#chipGradient)" />
        <circle cx="110" cy="80" r="2" fill="url(#chipGradient)" />
        <circle cx="110" cy="90" r="2" fill="url(#chipGradient)" />

        <path
          d="M 70 100 L 70 110 M 80 100 L 80 110 M 90 100 L 90 110"
          stroke="url(#chipGradient)"
          strokeWidth="1.5"
          strokeLinecap="round"
        />
        <circle cx="70" cy="110" r="2" fill="url(#chipGradient)" />
        <circle cx="80" cy="110" r="2" fill="url(#chipGradient)" />
        <circle cx="90" cy="110" r="2" fill="url(#chipGradient)" />

        <path
          d="M 20 70 L 10 70 M 20 80 L 10 80 M 20 90 L 10 90"
          stroke="url(#chipGradient)"
          strokeWidth="1.5"
          strokeLinecap="round"
        />
        <circle cx="10" cy="70" r="2" fill="url(#chipGradient)" />
        <circle cx="10" cy="80" r="2" fill="url(#chipGradient)" />
        <circle cx="10" cy="90" r="2" fill="url(#chipGradient)" />

        {/* Main chip outline */}
        <rect
          x="25"
          y="25"
          width="70"
          height="70"
          rx="8"
          stroke="url(#chipGradient)"
          strokeWidth="3"
          fill="rgba(0, 0, 0, 0.3)"
        />

        {/* AI Text */}
        <text
          x="60"
          y="70"
          textAnchor="middle"
          dominantBaseline="middle"
          className={`font-bold ${textSizes[size]}`}
          fill="url(#chipGradient)"
          style={{
            fontFamily: "system-ui, -apple-system, sans-serif",
            fontWeight: "bold",
            fontSize: size === "sm" ? "20" : size === "md" ? "28" : "40",
          }}
        >
          AI
        </text>
      </svg>
    </div>
  );
};

