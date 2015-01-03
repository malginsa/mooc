cmake_minimum_required(VERSION 2.8.4)
project(triangl)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES triangl.cpp triangl.h)
add_executable(triangl ${SOURCE_FILES})