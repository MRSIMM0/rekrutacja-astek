import React from "react";
import "./style.css";

export default function input({ placeholder, min, max, type, onChange }) {
  return (
    <div className="input">
      <input
        onChange={(event) => {
          onChange(event.target.value);
        }}
        className="input-in"
        placeholder={placeholder}
        min={min}
        max={max}
        type={type}
      />
    </div>
  );
}
