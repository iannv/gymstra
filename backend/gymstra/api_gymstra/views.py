from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import generics, permissions, status
from .serializers import AdministradorSerializer
from .models import Administrador

# Administrador
class RegistroView(APIView):
    def post(self, request, *args, **kwargs):
        serializer = AdministradorSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response('Administrador registrado', status=status.HTTP_201_CREATED)
        return Response('No se pudo registrar al administrador', 'Error: ', serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    
class AdministradorPerfilView(generics.RetrieveUpdateAPIView):
    serializer_class = AdministradorSerializer
    # permission_classes = [permissions.IsAuthenticated]
    permission_classes = [permissions.AllowAny]
    queryset = Administrador.objects.all()
    
    def get_object(self):
        return self.request.user


# Alumno


# CUOTA


# CLASE


# ASISTENCIA


# ZONA MUSCULAR



# EJERCICIO



# RUTINA
