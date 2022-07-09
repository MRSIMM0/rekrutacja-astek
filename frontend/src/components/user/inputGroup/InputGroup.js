import React from "react";
import "./style.css";
export default function InputGroup({ name, type, radioName, value }) {
  return (
    <div class="input-wrapper">
      <div class="input-group-chek">
        <div class="input-name">{name}</div>
        <input value={value} type={type} name={radioName}></input>
      </div>
    </div>
  );
}
