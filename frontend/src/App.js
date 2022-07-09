import "./App.css";
import MainView from "./components/MainView.js";
import Admin from "./components/admin/Admin.js";
import MainPage from "./components/main-page/MainPage";
import User from "./components/user/User.js";
import { useState, useEffect } from "react";

function App() {
  const [current, setCurrent] = useState();
  const [index, setIndex] = useState(-1);
  const [error, setError] = useState(false);

  const admin = <Admin />;

  const user = <User />;

  const handle = (state) => {
    if (state == "admin") {
      setCurrent(admin);
      setIndex(1);
      setError(false);
    }
    if (state == "user") {
      setCurrent(user);
      setIndex(1);
      setError(false);
    }

    if (state == "") {
      setError(true);
    }
  };

  useEffect(() => {
    setCurrent(<MainPage action={handle} />);
  }, []);

  return (
    <MainView
      hasError={error}
      zIndex={index}
      goBack={() => {
        setIndex(-1);
        setCurrent(<MainPage action={handle} />);
      }}
      component={current}
    ></MainView>
  );
}

export default App;
