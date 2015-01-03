#include <iostream>
#include <vector>

using namespace std;

struct Point {double x; double y; double z;};
struct Triangle {int first; int second; int third;}; // three numbers of points

vector<Point>AllPoints;

vector<Triangle>Mesh(const vector<Point>& Contour) {
    Triangle tr1 = {0,1,2};
    vector<Triangle> mesh;
    mesh.push_back(tr1);
    return mesh;
// Point.size() Point[i]
}

int main() {
    vector<Point>Contour = {{1,1,0}, {3,1,0}, {3,3,0}, {1,3,0}};
    vector<Point>AllPoints = {{1,1,0}, {3,1,0}, {3,3,0}, {1,3,0}, {2,2,0}};
    vector<Triangle>TrMesh = Mesh(Contour);
    cout << Contour[1].y << endl;
    cout << TrMesh[0].third << endl;
    return 0;
}
