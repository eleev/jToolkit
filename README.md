# jToolkit  [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

[![Language](https://img.shields.io/badge/language-Java-orange.svg)]()
[![Language](https://img.shields.io/badge/language-OpenGL-blue.svg)]()
[![Language](https://img.shields.io/badge/shading%20language-GLSL-lightgray.svg)]()
[![Status](https://img.shields.io/badge/status-Legacy%20Code%20(2013)-green.svg)]()
[![License](https://img.shields.io/badge/licence-MIT-yellow.svg)]()

**Last Update: 12/September/2018.**

# ✍️ About 
A collection of various utilities, classes and shaders for `Java`+`OpenGL`+`GLSL`(fixed function pipeline mostly) programming.  The sources were originally developed for education pusposes and then I decided to open source everything.

# 🏗 How to Get Started
Simply copy-paste `shader programms` and `classes` into your project. Import them to class(es) that you need to and you're good to go. 

# 📝 Notes
* You might see that the author in some places is `Virus`. This is my nickname.
* The used assets were found on the `Web`. *Do not use them for commertial purposes*. They are only used for demostration.
* My appologies for the lack of comments and proper explanation of what going on inside of complex shaders and classes.
* The main reason for publishing this code was to share some groundwork related to `OpenGL's` fixed function pipeline, which may be useful for some developers on early stage learnerns

# 📚 Contents
[*] *The term `Object` referes to the commonly used naming convention in `OpenGL` (`Vertex Buffer Object`, `Vertex Index Object` etc.) and has no relation to `Object-Oriented Paradigm`.*

- [Accumulation Buffer](https://github.com/jVirus/jToolkit/blob/master/src/tasks/AccumulationBuffer.java) - high-level tool for `OpenGL's Accumulation Buffer`
- [Stencil Buffer](https://github.com/jVirus/jToolkit/blob/master/src/tasks/StencilBuffer.java) - high-level tool for `OpenGL's Stencil Buffer`
- [Walkable Goblin Demo](https://github.com/jVirus/jToolkit/blob/master/src/tasks/WalkingGoblinDemo.java) - demonstrates an `animation` approach related to 3D model and how programmatically you can build `mirrors`
- [Various drawing methods](https://github.com/jVirus/jToolkit/blob/master/src/tasks/DrawingMethods.java) - high-level tools for `immediate mode drawing`, `display lists`, `VAO` and `VBO`
- [Camera PP](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/camera/Camera.java) - implementation of a `camera` for programmable rendering pipeline
- [Image Loader](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/loader/image/ImageLoader.java) 
- [Model Loader](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/loader/model/Loader.java)
- [Model Object](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/model/Model.java) - 
- [Texture Object](https://github.com/jVirus/jToolkit/blob/master/src/jToolkit4ProgPipeline/file/texture/Texture.java) - 
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

Also examples of:
- [Prgrammable Rendering Pipeline](https://github.com/jVirus/jToolkit/blob/master/src/main/ProgrammablePipeline.java)
- [Fixed Function Rendering Pipeline](https://github.com/jVirus/jToolkit/blob/master/src/main/FixedPipeline.java)


# 👨‍💻 Author
[Astemir Eleev](https://github.com/jVirus)

# 🔖 License
`jToolkit` is realeased under the [MIT License](https://github.com/jVirus/jToolkit/blob/master/LICENSE)
