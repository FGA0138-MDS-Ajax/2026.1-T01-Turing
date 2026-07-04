import React from 'react';
import { Navigate } from 'react-router-dom';

export const AdminRoute = ({ children }: { children: React.ReactNode }) => {
  const token = localStorage.getItem("token");

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const payload = JSON.parse(window.atob(base64));
    const adminsAutorizados = ["adm@gmail.com", "seu-email@unb.br"];
    const ehAdmin = adminsAutorizados.includes(payload.sub);

    if (!ehAdmin) {
      return <Navigate to="/agendamentos" replace />;
    }
  } catch (e) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
};