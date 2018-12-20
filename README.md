# jToolkit  [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

[![Language](https://img.shields.io/badge/language-Java-orange.svg)]()
[![Language](https://img.shields.io/badge/language-OpenGL-blue.svg)]()
[![Language](https://img.shields.io/badge/shading%20language-GLSL-lightgray.svg)]()
[![Status](https://img.shields.io/badge/status-Legacy%20Code%20(2013)-green.svg)]()
[![License](https://img.shields.io/badge/licence-MIT-yellow.svg)]()

**Last Update: 12/September/2018.**

### If you like the project, please give it a star ⭐ It will show the creator your appreciation and help others to discover the repo.

# ✍️ About 
A collection of various utilities, classes and shaders for `Java`+`OpenGL`+`GLSL`(fixed function pipeline mostly) programming.  The sources were originally developed for education pusposes and then I decided to open source everything.

# ⚠️ Warning
- The sources are legacy and were originally developed using `JDK 5.0`, `JDK 6.0`, `OpenGL 1.0`, `OpenGL 1.1`, `OpenGL 1.2`, `OpenGL 2.0`,  and `OpenGL 3.1` - take it into consideration if you decide to use the sources.
- The used [assets](https://github.com/jVirus/jToolkit/tree/master/res) were found on the `Web` and cannot be used in commertial purposes.

# 🏗 How to Get Started
Simply copy-paste `shader programms` and `classes` into your project. Import them to class(es) that you need to and you're good to go. 

# 📝 Notes
* You might see that the author in some places is `Virus`. This is my nickname.
* The used assets were found on the `Web`. *Do not use them for commertial purposes*. They are only used for demostration.
* My appologies for the lack of comments and proper explanation of what going on inside of complex shaders and classes.
* The main reason for publishing this code was to share some groundwork related to `OpenGL's` fixed function pipeline, which may be useful for some developers on early stage learnerns

# 📚 Contents
[*] *The term `Object` referes to the commonly used naming convention in `OpenGL` (`Vertex Buffer Object`, `Vertex Index Object` etc.) and has no relation to `Object-Oriented Paradigm`.*

Tools, Data Structures and Utilities:
- [Accumulation Buffer](https://github.com/jVirus/jToolkit/blob/master/src/tasks/AccumulationBuffer.java) - high-level tool for `OpenGL's Accumulation Buffer`
- [Stencil Buffer](https://github.com/jVirus/jToolkit/blob/master/src/tasks/StencilBuffer.java) - high-level tool for `OpenGL's Stencil Buffer`
- [Walkable Goblin Demo](https://github.com/jVirus/jToolkit/blob/master/src/tasks/WalkingGoblinDemo.java) - demonstrates an `animation` approach related to 3D model and how programmatically you can build `mirrors`
- [Various drawing methods](https://github.com/jVirus/jToolkit/blob/master/src/tasks/DrawingMethods.java) - high-level tools for `immediate mode drawing`, `display lists`, `VAO` and `VBO`
- [Camera PP](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/camera/Camera.java) - implementation of a `camera` for programmable rendering pipeline
- [Image Loader](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/loader/image/ImageLoader.java) 
- [Model Loader](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/loader/model/Loader.java)
- [Model Object](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/model/Model.java) 
- [Texture Object](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/texture/Texture.java) 
- [Timer Utility](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/utils/Timer.java)
- [Matrix Utility](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4ProgPipeline/file/utils/matrixStack)
- [Buffer Tools](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/utils/buffer/BufferTools.java)
- [Various Camera Implementations for FPP](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/camera) - `First person` and `Euler` camera implementations
- [Color Objects](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/color)
- [FPS Meter](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/common/FPSmeter.java)
- [GLUT](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/common/GLUT.java)
- [Meth Tools](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/common/MathTools.java)
- [Shader Program Object](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/common/ShaderProgram.java)
- [Timer](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/common/Timer.java)
- [Buffer Utils 1](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/common/BufferUtils1.java)
- [Buffer Utils 2](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/common/BufferUtils2.java)
- [Image Processor](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/image/ImgProcessor.java)
- [Screenshooter](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/image/Screenshoter.java)
- [JPEG Drawer](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/image/JpgDrawer.java)
- [Various Image Readers: PNG, RAW, TWL, TARGA, Height Map](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/image/reader)
- [Sprite Sheet and Entity Rendering](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/image/sprite)
- [Texture Loader/Uploader Objects](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/image/texture)
- [Ketframe Animation Utilities](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/keyframeanim)
- [Lightning](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/lighting/Lighting.java)
- [Shadow Mapping and Gereration](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4FixedPipeline/lighting/shadow/ShadowMap.java)
- [Matrix Utilities](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/matrix)
- [Model Representation: Face and Model](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/object/datastructure)
- [OBJ Drawer, Loader and Parser](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/object/instruments)
- [Particle System](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/particle)
- [Physics Primitives: AABB2d, AABB3d, Bounding Boxes, Bounding Volumes and vairous physics shapes](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/physics)
- [Point Data Structure](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/point)
- [Primitive Geometric Objects](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/primitives)
- [Quaternion Data Structure](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/quaterion)
- [Cubic and Spheric Skyboxes](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/skybox)
- [Terrain](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/terrain)
- [Vector Data Structre](https://github.com/jVirus/jToolkit/tree/master/src/jToolkit4FixedPipeline/vector)

Examples of:
- [Prgrammable Rendering Pipeline](https://github.com/jVirus/jToolkit/blob/master/src/main/ProgrammablePipeline.java)
- [Fixed Function Rendering Pipeline](https://github.com/jVirus/jToolkit/blob/master/src/main/FixedPipeline.java)

Shaders:
- [Base Rendering](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/New%20shaders/Base)
- [Texturing](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/New%20shaders/Base)
- [Twist Effect](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/New%20shaders/Twist)
- [NVIDIA'S GPU Gems Implementation for Wave Simulation](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/New%20shaders/Waves)
- [Lamberian Light Model](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/Lambertian%20lighting)
- [Phong Light Model](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/Phong%20lighting)
- [Ambient Light](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/ambient%20lighting)
- [Colorize](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/color%20setter)
- [Diffuse Light](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/diffuse%20lighting)
- [Flatten Shading](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/flatten)
- [Landscape/Hightmap Helper](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/landscape)
- [Per-fragment Phong Light Model](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/per%20fragment%20Phong%20lighting)
- [Per-fragment Lighting](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/per%20fragment%20lighting)
- [Per-pixel Phong Light Model](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/per%20pixel%20Phong%20lighting)
- [Per-pixel Point Light Model](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/per%20pixel%20light%20point)
- [Per-pixel Spot Light](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/per%20pixel%20spot%20light)
- [Per-pixel Toon Shading](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/per%20vertex%20toon%20shading)
- [Plastic Lighing](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/plastic%20lighting)
- [Specular Lighting 01](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/specular%20lighting%20(00))
- [Specular Lighting 02](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/specular%20lighting%20(01))
- [Toon Shading](https://github.com/jVirus/jToolkit/tree/master/shader%20programs/Old%20shaders/toon%20shading)

# 👨‍💻 Author
[Astemir Eleev](https://github.com/jVirus)

# 🔖 License
`jToolkit` is realeased under the [MIT License](https://github.com/jVirus/jToolkit/blob/master/LICENSE)
