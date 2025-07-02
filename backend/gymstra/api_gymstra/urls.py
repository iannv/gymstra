from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import *

router = DefaultRouter()
router.register(r'alumnos', AlumnoView, basename='alumnos')
router.register(r'cuotas', CuotaView , basename='cuotas')
router.register(r'clases', ClaseView, basename='clases')
router.register(r'asistencias', AsistenciaView, basename='asistencias')
router.register(r'zona-muscular', ZonaMuscularView, basename='zona-muscular')
router.register(r'ejercicios', EjercicioView, basename='ejercicios')
router.register(r'rutinas', RutinaView, basename='rutinas')


urlpatterns = [
    path('', include(router.urls)),
    
    path('registrarse/', RegistroView.as_view(), name='registrarse'),
    path('iniciar-sesion/', IniciarSesionView.as_view(), name='iniciar-sesion'),
    
    path('perfil/', AdministradorPerfilView.as_view(), name='perfil'),   
]