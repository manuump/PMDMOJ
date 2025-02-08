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

### Version 1.4 - Navigation Drawer y Toolbar

En esta versión, se ha implementado el **Navigation Drawer** y la **Toolbar** para mejorar la navegación en la aplicación.

- **Navigation Drawer**:  
  Se añadió un menú lateral (drawer) con opciones como "Eventos", "Comentarios" y "Cerrar sesión". El menú es accesible desde un icono en la esquina superior izquierda.

- **Toolbar**:  
  Se añadió una barra de herramientas superior para mejorar la experiencia de usuario. Incluye botones de opciones en la parte derecha para acceder a funcionalidades adicionales.

Con estas mejoras, la navegación en la aplicación es ahora más fluida y accesible.

## Version 2.1 - Reestructuración MVVM y Fragmentos de Eventos y Comentarios

En esta versión, hemos realizado una reestructuración significativa de la aplicación al adoptar el patrón de diseño **Model-View-ViewModel (MVVM)**, mejorando la organización del código y la separación de responsabilidades. Además, los fragmentos de **Eventos** y **Comentarios** se han integrado para ofrecer una experiencia más dinámica.

### **Novedades de la versión 2.1:**

- **Reestructuración a MVVM**:
  - La lógica de la pantalla de eventos, que estaba anteriormente en la Activity (`EventosActivity`), se ha trasladado al **ViewModel** correspondiente. Ahora, la Activity solo se encarga de gestionar la UI y las interacciones con el usuario, mientras que el **ViewModel** maneja la lógica de negocio y la comunicación con los datos.
  - Se ha creado un **ViewModel** específico para los fragmentos de eventos y comentarios, gestionando el flujo de datos y las actualizaciones de la interfaz de forma más eficiente.

- **Fragmentos de Eventos y Comentarios**:
  - El fragmento de **Eventos** se encarga de mostrar y gestionar la lista de eventos. Utiliza un **RecyclerView** para mostrar los eventos disponibles y permite añadir, editar y eliminar eventos desde el fragmento.
  - El fragmento de **Comentarios** permite a los usuarios interactuar y dejar sus opiniones sobre los eventos. Los comentarios se gestionan a través de un **RecyclerView** similar al de eventos, y el sistema permite a los usuarios añadir nuevos comentarios.

- **Nueva estructura de proyecto**:
  - **Adapter**: Gestiona la adaptación de los datos a la interfaz de usuario. Esto incluye el adaptador para el `RecyclerView` que muestra los eventos y comentarios.
  - **Data**:
    - **Models**: Se han reorganizado las clases modelo, y se ha añadido la clase **EventoViewModel** para gestionar la lógica del modelo de eventos y la clase **ComentarioViewModel** para los comentarios.
    - **Repository**: Se ha añadido una capa de **Repository** para gestionar los datos de la aplicación, como la obtención de eventos y comentarios desde diversas fuentes (por ejemplo, Firebase), mejorando la separación de responsabilidades y la reutilización de código.
  - **UI**: La capa de interfaz de usuario, que incluye los **fragments** y **activities**, interactúa con el `ViewModel` y presenta los datos al usuario. Ahora la pantalla de eventos y comentarios se maneja mediante fragmentos específicos.

Con estas mejoras, la aplicación es más escalable, mantenible y eficiente. La adopción de MVVM permite que la lógica y la interfaz de usuario estén claramente separadas, lo que facilita la implementación de nuevas funcionalidades en el futuro.
