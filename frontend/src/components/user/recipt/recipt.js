import React from "react";
import "./style.css";
import Input from "../../utils/input/input";
import Button from "../../utils/button/button";
import { useState } from "react";

export default function Recipt({ name, index, handleInput }) {
  const onChange = (val) => {
    let object = {
      index: index,
      receiptName: name,
      amount: val,
    };
    handleInput(object);
  };

  return (
    <div className="recipt-main">
      <div className="title"> {name}</div>
      <div className="input-group">
        <div>Ammount</div>
        <div className="input-smalll">
          <Input
            onChange={(e) => {
              onChange(e);
            }}
          ></Input>
        </div>
        <div>USD</div>
      </div>
    </div>
  );
}
