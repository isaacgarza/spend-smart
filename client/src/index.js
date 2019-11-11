import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import { BrowserRouter } from "react-router-dom";

ReactDOM.render(
  //The whole application is wrapped inside BrowserRouter
  //This allows access for all the components provided
  //by React Router
  <BrowserRouter>
    <App />
  </BrowserRouter>,
  document.getElementById("root")
);
