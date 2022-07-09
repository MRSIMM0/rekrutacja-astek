import React from "react";
import Button from "../../utils/button/button";
export default function Limit({ name, limit, getLim }) {
  const deleteL = (name) => {
    var body = {
      limitType: name,
    };

    fetch("http://localhost:8080/api/deleteLimit", {
      method: "POST",
      headers: {
        User: "Admin",
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(body),
    })
      .then((response) => response.json())
      .then((res) => {
        console.log(res);
        getLim(res);
      });
  };
  return (
    <div className="receipt">
      <div className="title">{name}</div>
      <div className="wrap">
        <div>{limit}</div>
        <div>USD</div>
        <div className="x">
          <Button action={() => deleteL(name)}>X</Button>
        </div>
      </div>
    </div>
  );
}
