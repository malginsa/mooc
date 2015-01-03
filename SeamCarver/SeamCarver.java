// Image-aware scaling

import java.awt.Color;

public class SeamCarver {

  private class Vert implements Comparable<Vert>
  // vertex of image
  {
    int x, y; // coondinates
    int dist; // distance to root

    public Vert(int x, int y, int dist)
    {
      this.x = x;
      this.y = y;
      this.dist = dist;
    }

    public int compareTo(Vert that)
    {
      if (this.dist < that.dist) return -1;
      else if (this.dist > that.dist) return 1;
      else return 0;
    }
  }

  private Picture pic; // current picture
//  private int[][] energy; // energy of every pixel
  private int[][] edgeTo; // "from" vertex's coordinate
//  private int[][] distTo; // distance of shortest path
    // priority queue for shortest path algorithm 
    // index of pq is vertid, wich is equal x + y * W
  private MinPQ<Vert> pq;
  private final int BORDERENERGY; // energy of border pixel
  private final int VERTIC = 0; // direction of Seam
  private final int HORIZ = 1;
  private final int UNVIZITED = -1; // prohibited coordinate of pixel 

  // create a seam carver object based on the given picture
  public SeamCarver(Picture picture)
  {
    this.BORDERENERGY = 195075;
    this.pic = new Picture(picture);
//    int width = this.pic.width();
//    int height = this.pic.height();

// there was energy calculation
//    energy = new int[width][height];
//    // charge border pixels of maximum energy
//    for (int x = 0; x < width; x++)
//    {
//      energy[x][0] = this.BORDERENERGY;
//      energy[x][height - 1] = this.BORDERENERGY;
//    }
//    for (int y = 1; y < height- 1; y++)
//    {
//      energy[0][y] = this.BORDERENERGY;
//      energy[width - 1][y] = this.BORDERENERGY;
//    }
//    for (int x = 1; x < width - 1; x++)
//      for (int y = 1; y < height- 1; y++)
//      {
//        ...
//      }
  }

  // current picture
  public Picture picture()
  {
    return this.pic;
  }

  // width of current picture
  public int width()
  {
    return this.pic.width();
  }

  // height of current picture
  public int height()
  {
    return this.pic.height();
  }

  // energy of pixel at column x and row y
  public double energy(int x, int y)
  {
    int W = this.pic.width();
    int H = this.pic.height();
    if (x < 0 || x > W - 1)
      throw new java.lang.IndexOutOfBoundsException();
    if (y < 0 || y > H - 1)
      throw new java.lang.IndexOutOfBoundsException();

    // border pixel
    if (x == 0 || x == W - 1 || y == 0 || y == H - 1)
      return this.BORDERENERGY;

    Color left = this.pic.get(x - 1, y);
    Color right = this.pic.get(x + 1, y);
    Color up = this.pic.get(x, y - 1);
    Color down = this.pic.get(x, y + 1);
    int rx = left.getRed() - right.getRed();
    int gx = left.getGreen() - right.getGreen();
    int bx = left.getBlue() - right.getBlue();
    int ry = up.getRed() - down.getRed();
    int gy = up.getGreen() - down.getGreen();
    int by = up.getBlue() - down.getBlue();
    return rx*rx + gx*gx + bx*bx + ry*ry + gy*gy + by*by;
  }

  private void initglobals()
  {
    int W = this.pic.width();
    int H = this.pic.height();
    pq = new MinPQ<Vert>(W + H);
    edgeTo = new int[W][H];
//    distTo = new int[W][H];
    // inizialize internal variables
    for (int x = 0; x < W; x++)
      for (int y = 0; y < H; y++)
      {
        edgeTo[x][y] = UNVIZITED;
//        distTo[x][y] = BORDERENERGY * H; // max possible path weight
      }
  }

  private void relax(Vert tail, int xhead, int yhead, int direct)
  // relax vertex head(edge destination) from tail(edge source)
  // direct is direction of Seam
  {
    if (edgeTo[xhead][yhead] != UNVIZITED) // already visited
      return;

    int W = this.pic.width();
    int H = this.pic.height();
    
    // whether vertex head belongs to border?
    if (xhead == 0 || xhead == W - 1 || yhead == 0 || yhead == H - 1)
      return;
    
    int disthead = tail.dist + (int) energy(xhead, yhead);
    pq.insert(new Vert(xhead, yhead, disthead));
    if (direct == VERTIC)
      edgeTo[xhead][yhead] = tail.x;
    else
      edgeTo[xhead][yhead] = tail.y;
  }

  // fill seam of length len with border pixels
  private int[] borderseam(int len)
  {
    int[] ret = new int[len];
    for (int i = 0; i < len; i++)
      ret[i] = 1;
    return ret;
  }

  // sequence of indices for horizontal seam
  public int[] findHorizontalSeam()
  {
    int last = -1; // y-coordinate of the shortest path's last vertex 
    int W = this.pic.width();
    int H = this.pic.height();

    if (H == 2 || W == 2)
      return borderseam(W);

    initglobals();
    // fill second raw vertexes in queue 
    for (int y = 1; y < H - 1; y++)
    {
//      edgeTo[1][y] = y; // unnecessary
//      distTo[1][y] = (int)energy(1, y);
      pq.insert(new Vert(1, y, (int) energy(1, y)));
    }

    while (!pq.isEmpty())
    {
      Vert vert = pq.delMin();
      if (vert.x == W - 2) // min path has founded
      {
        last = vert.y;
        break;
      }
      // check notheastern vertex
      relax(vert, vert.x + 1, vert.y - 1, HORIZ);
      // check nothern vertex
      relax(vert, vert.x + 1, vert.y, HORIZ);
      // check southeastern vertex
      relax(vert, vert.x + 1, vert.y + 1, HORIZ);
    }

    // fill up seam
    if (last == -1)
      throw new java.util.NoSuchElementException();
    int[] seam = new int[W];
    seam[W - 1] = last;
    for (int x = W - 2; x > 0; x--)
    {
      seam[x] = last;
      last = edgeTo[x][last];
    }
    seam[0] = seam[1];
    return seam;
  }

  // sequence of indices for vertical seam
  // we will search path only from 2nd to (column-2)'st raws
  // not to get to account border's vertex
  public int[] findVerticalSeam()
  {
    int last = -1; // x-coordinate of the shortest path's last vertex 
    int W = this.pic.width();
    int H = this.pic.height();

    if (H == 2 || W == 2)
      return borderseam(H);

    initglobals();
    // fill second column vertexes in queue 
    for (int x = 1; x < W - 1; x++)
    {
//      edgeTo[x][1] = x;
//      distTo[x][1] = (int)energy(x, 1);
      pq.insert(new Vert(x, 1, (int) energy(x, 1)));
    }

    while (!pq.isEmpty())
    {
      Vert vert = pq.delMin();
      if (vert.y == H - 2) // min path has founded
      {
        last = vert.x;
        break;
      }
      // check southwestern vertex
      relax(vert, vert.x - 1, vert.y + 1, VERTIC);
      // check southern vertex
      relax(vert, vert.x, vert.y + 1, VERTIC);
      // check southeastern vertex
      relax(vert, vert.x + 1, vert.y + 1, VERTIC);
    }

    // fill up seam
    if (last == -1)
      throw new java.util.NoSuchElementException();
    int[] seam = new int[H];
    seam[H - 1] = last;
    for (int y = H - 2; y > 0; y--)
    {
      seam[y] = last;
      last = edgeTo[last][y];
    }
    seam[0] = seam[1];
    return seam;
  }

  // remove horizontal seam from current picture
  public void removeHorizontalSeam(int[] seam)
  {
    if (seam == null)
      throw new java.lang.NullPointerException();

    int W = this.pic.width();
    int H = this.pic.height();

    if (seam.length != W)
      throw new java.lang.IllegalArgumentException();
    for (int x = 0; x < W; x++)
      if (seam[x] < 0 || seam[x] > H - 1)
        throw new java.lang.IllegalArgumentException();
    for (int x = 0; x < W - 1; x++)
      if (Math.abs(seam[x] - seam[x + 1]) > 1)
        throw new java.lang.IllegalArgumentException();
    if (W <= 1 || H <= 1)
        throw new java.lang.IllegalArgumentException();

    int newheight = H - 1;
    Picture newpic = new Picture(W, newheight);
    for (int x = 0; x < W; x++)
    {
      for (int y = 0; y < seam[x]; y++)
        newpic.set(x, y, this.pic.get(x, y));
      for (int y = seam[x]; y < newheight; y++)
        newpic.set(x, y, this.pic.get(x, y + 1));
    }
    this.pic = newpic;
  }

  // remove vertical seam from current picture
  public void removeVerticalSeam(int[] seam)
  {
    if (seam == null)
      throw new java.lang.NullPointerException();

    int W = this.pic.width();
    int H = this.pic.height();

    if (seam.length != H)
      throw new java.lang.IllegalArgumentException();
    for (int y = 0; y < H; y++)
      if (seam[y] < 0 || seam[y] > W - 1)
        throw new java.lang.IllegalArgumentException();
    for (int y = 0; y < H - 1; y++)
      if (Math.abs(seam[y] - seam[y + 1]) > 1)
        throw new java.lang.IllegalArgumentException();
    if (W <= 1 || H <= 1)
        throw new java.lang.IllegalArgumentException();

    int newwidth = W - 1;
    Picture newpic = new Picture(newwidth, H);
    for (int y = 0; y < H; y++)
    {
      for (int x = 0; x < seam[y]; x++)
        newpic.set(x, y, this.pic.get(x, y));
      for (int x = seam[y]; x < newwidth; x++)
        newpic.set(x, y, this.pic.get(x + 1, y));
    }
    this.pic = newpic;
  }

  // unit testing
  public static void main(String[] args)
  {
  }
}
