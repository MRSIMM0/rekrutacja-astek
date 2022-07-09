import React from "react";
import "./style.css";
export default function button({ children, action }) {
  return (
    <div onClick={() => action()} className="button">
      {children}
    </div>
  );
}
