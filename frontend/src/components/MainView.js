import React from "react";
import "./style.css";
import { useState } from "react";
import Button from "../components/utils/button/button";
export default function MainView({ component, hasError, zIndex, goBack }) {
  const error = hasError ? (
    <div className="hasError">Chose an option</div>
  ) : null;



  const back = () => {
    goBack();
  };

  return (
    <div class="container">
      <div class="card" id="card">
        <div className="backBtn" style={{ zIndex: zIndex }}>
          <Button action={back}>Back</Button>
        </div>
        {component}
        {error}
      </div>
    </div>
  );
}
