import React from "react";
import "./App.css";
import NavHeader from "./components/NavHeader";
import AppRoutes from "./AppRoutes";

export default function App() {
  return (
    <div>
      <NavHeader />
      <AppRoutes />
    </div>
  );
}
