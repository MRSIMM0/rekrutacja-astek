import React from "react";
import "./style.css";
import Button from "../utils/button/button";

import { useState } from "react";
export default function MainPage({ action }) {
  const [state, setState] = useState("");

  const handleSubmit = () => {
    action(state);
  };

  return (
    <div class="main-form">
      <select
        value={state}
        onChange={(event) => {
          setState(event.target.value);
        }}
        id="mode"
        name="role"
      >
        <option value="">-- Please choose your role --</option>
        <option value="user">User</option>
        <option value="admin">Admin</option>
      </select>
      <div class="butto">
        <Button id="access" action={handleSubmit}>
          Access
        </Button>
      </div>
      <div class="error"> error</div>
    </div>
  );
}
