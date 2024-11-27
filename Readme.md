# OCIOJAEN - Tu guía de ocio en Jaén

Bienvenido a **OCIOJAEN**, la aplicación que te conecta con todo lo que hay para hacer en la ciudad de Jaén. Ya sea que estés buscando eventos, lugares para visitar, actividades o planes para el fin de semana, ¡OCIOJAEN es tu compañero perfecto!

En este proyecto, desarrollamos una interfaz intuitiva y fácil de usar, que te permitirá descubrir qué hacer en Jaén en cualquier momento. Desde restaurantes hasta conciertos, pasando por actividades al aire libre, nuestra app te mantiene informado sobre el mejor ocio de la ciudad.

### Características
- **Eventos locales:** Mantente al día con los próximos eventos.
- **Recomendaciones personalizadas:** Basado en tus intereses, te sugerimos las mejores actividades.
- **Interfaz intuitiva:** Diseñada para que navegar sea fácil y rápido.
- **Login de usuarios:** Sistema de autenticación que permite a los usuarios registrarse e iniciar sesión para una experiencia personalizada.

Únete a nosotros y comienza a explorar Jaén de una forma diferente. ¡Diviértete y haz que cada día sea una nueva aventura!

## CONTROL DE VERSIONES : 

## CONTROL DE VERSIONES : 

### Version 1.1 - RecyclerView, POJO y Adapter

En esta versión, he utilizado **RecyclerView**, **POJO** y **Adapter**. En mi proyecto he generado dos nuevos paquetes llamados: **Models** y **Adapter**, y un nuevo Activity **EventosActivity**.

- **Models**:  
  La clase `Evento` es una clase de datos (POJO) que representa un evento con tres propiedades:
  
  - **Titulo**: Un `String` que contiene el título del evento.
  - **Descripcion**: Un `String` que describe brevemente el evento.
  - **Imagen**: Un `String` que contiene el nombre o referencia de la imagen asociada al evento (un nombre de archivo de recursos `drawable`).

- **Adapter**:  
  La clase `EventoAdapter` es un adaptador personalizado para un `RecyclerView` que muestra una lista de eventos. Cada evento tiene un título, una descripción, una imagen y un botón para eliminarlo.

- **EventosActivity**:  
  La clase `EventosActivity` es una Activity en Android que gestiona la lista de eventos mediante un `RecyclerView`, utilizando un adaptador personalizado (`EventoAdapter`) para mostrar los eventos en la interfaz de usuario.

