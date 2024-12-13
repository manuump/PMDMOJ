# OCIOJAEN - Tu guía de ocio en Jaén

Bienvenido a **OCIOJAEN**, la aplicación que te conecta con todo lo que hay para hacer en la ciudad de Jaén. Ya sea que estés buscando eventos, lugares para visitar, actividades o planes para el fin de semana, ¡OCIOJAEN es tu compañero perfecto!

En este proyecto, desarrollamos una interfaz intuitiva y fácil de usar, que te permitirá descubrir qué hacer en Jaén en cualquier momento. Desde restaurantes hasta conciertos, pasando por actividades al aire libre, nuestra app te mantiene informado sobre el mejor ocio de la ciudad.

## Características
- **Eventos locales:** Mantente al día con los próximos eventos.
- **Recomendaciones personalizadas:** Basado en tus intereses, te sugerimos las mejores actividades.
- **Interfaz intuitiva:** Diseñada para que navegar sea fácil y rápido.
- **Login de usuarios:** Sistema de autenticación que permite a los usuarios registrarse e iniciar sesión para una experiencia personalizada.

Únete a nosotros y comienza a explorar Jaén de una forma diferente. ¡Diviértete y haz que cada día sea una nueva aventura!

---

## Control de Versiones

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

### Version 1.2 - Edición y Alta de Eventos

En esta versión, he agregado funcionalidades para **editar** y **añadir** eventos en la lista existente mediante el uso de **DialogFragment**.

- **Funcionalidad de Alta**:  
  Se añadió un botón flotante que permite al usuario añadir un nuevo evento. Al pulsarlo, se abre un `DialogFragment` que permite capturar los datos del nuevo evento. Al confirmar, el nuevo evento se agrega a la lista y se actualiza el `RecyclerView`.

- **Funcionalidad de Edición**:  
  Cada evento ahora incluye un botón adicional para editar. Al pulsar este botón, se abre un `DialogFragment` con los datos del evento pre-rellenados. El usuario puede modificar estos datos y, al confirmar, el evento se actualiza en la lista y en el `RecyclerView`.

### Version 1.3 - Integración de Firebase y Gestión de Sesiones

En esta versión, hemos integrado **Firebase Authentication** para proporcionar funcionalidades avanzadas de autenticación, como **registro de usuarios**, **inicio de sesión** y **recuperación de contraseña**. Esto mejora significativamente la experiencia del usuario al garantizar un sistema seguro y confiable.

#### **Funcionalidades añadidas**:
- **Registro de usuarios**:  
  Los usuarios pueden registrarse utilizando su correo electrónico y una contraseña. Firebase valida automáticamente las credenciales y asegura los datos de los usuarios.

- **Inicio de sesión**:  
  Permite a los usuarios autenticarse y acceder a la aplicación utilizando su correo electrónico y contraseña. Si el correo no está verificado, se notifica al usuario para que complete este paso.

- **Recuperación de contraseña**:  
  Los usuarios pueden restablecer su contraseña si la olvidan, gracias a la funcionalidad de recuperación integrada de Firebase.

#### **Gestión de Sesiones con Preferencias Compartidas**:
Para mejorar la experiencia del usuario, hemos implementado **SharedPreferences** para guardar el estado de inicio de sesión. Esto permite que:
- Si el usuario cierra y reabre la aplicación, no sea redirigido al login si ya ha iniciado sesión anteriormente.
- Cuando el usuario se desloguea, las preferencias se eliminan, asegurando que regrese a la pantalla de inicio de sesión al abrir la aplicación.
