import React from "react";
import Button from "../../utils/button/button";
import Input from "../../utils/input/input";
import { useState } from "react";
import "./receipt.css";

export default function Receipt({ name, getRec, limit }) {
  const [lim, setLim] = useState(limit);

  const deleteR = () => {
    var bod = {
      receiptName: name,
    };

    fetch("http://localhost:8080/api/deleteRecipe", {
      method: "POST",
      headers: {
        User: "Admin",
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(bod),
    })
      .then((response) => response.json())
      .then((res) => {
        getRec(res);
      });
  };

  const addLimit = (limit) => {
    var bod = {
      receiptName: name,
      limit: limit,
    };

    fetch("http://localhost:8080/api/updateRecipe", {
      method: "POST",
      headers: {
        User: "Admin",
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(bod),
    })
      .then((response) => response.json())
      .then((res) => {
        getRec(res);
      });
  };

  return (
    <div className="receipt">
      <div className="title">{name}</div>
      <div className="wprapper">
        <div>Limit:</div>
        <div className="input-width">
          <Input
            onChange={(value) => setLim(value)}
            placeholder={limit}
          ></Input>
        </div>
        <div>USD</div>
        <Button action={() => addLimit(lim)}>SAVE</Button>
      </div>
      <div className="x">
        <Button action={() => deleteR(name)}>X</Button>
      </div>
    </div>
  );
}
