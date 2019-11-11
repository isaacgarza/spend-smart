import React from "react";
import NavHeader from "./components/NavHeader";
import AppRoutes from "./routes/AppRoutes";

export default function App() {
  return (
    <div>
      <NavHeader />
      <AppRoutes />
    </div>
  );
}
