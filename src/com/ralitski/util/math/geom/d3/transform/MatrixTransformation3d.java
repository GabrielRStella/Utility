package com.ralitski.util.math.geom.d3.transform;

import com.ralitski.util.math.Matrix;
import com.ralitski.util.math.geom.d3.Line3d;
import com.ralitski.util.math.geom.d3.Point3d;
import com.ralitski.util.math.geom.d3.Vector3d;

public class MatrixTransformation3d implements Transformation3d {
	
	//scale
	
	public static Matrix scaleMatrix(float scale) {
		return scaleMatrix(scale, scale, scale);
	}
	
	public static Matrix scaleMatrix(float x, float y, float z) {
		return Matrix.transformScale(x, y, z);
	}
	
	public static Matrix scaleMatrix(Matrix old, float scale) {
		return scaleMatrix(old, scale, scale, scale);
	}
	
	public static Matrix scaleMatrix(Matrix old, float x, float y, float z) {
		return Matrix.transformScale(old, x, y, z);
	}
	
	//translate
	
	public static Matrix translateMatrix(Point3d p) {
		return translateMatrix(p.getX(), p.getY(), p.getZ());
	}
	
	public static Matrix translateMatrix(Vector3d v) {
		return translateMatrix(v.getX(), v.getY(), v.getZ());
	}
	
	public static Matrix translateMatrix(float x, float y, float z) {
		return Matrix.transformTranslate(x, y, z);
	}
	
	public static Matrix translateMatrix(Matrix old, Point3d p) {
		return translateMatrix(old, p.getX(), p.getY(), p.getZ());
	}
	
	public static Matrix translateMatrix(Matrix old, Vector3d v) {
		return translateMatrix(old, v.getX(), v.getY(), v.getZ());
	}
	
	public static Matrix translateMatrix(Matrix old, float x, float y, float z) {
		return Matrix.transformTranslate(old, x, y, z);
	}
	
	//simple rotations
    
    public static Matrix rotateAroundXMatrix(float angle) {
    	Matrix m = Matrix.identity(4);
    	float sin = (float)Math.sin(angle);
    	float cos = (float)Math.cos(angle);
    	m.setValue(2, 2, cos);
    	m.setValue(3, 3, cos);
    	m.setValue(3, 2, sin);
    	m.setValue(2, 3, -sin);
    	return m;
    }
    
    public static Matrix rotateAroundYMatrix(float angle) {
    	Matrix m = Matrix.identity(4);
    	float sin = (float)Math.sin(angle);
    	float cos = (float)Math.cos(angle);
    	m.setValue(1, 1, cos);
    	m.setValue(3, 3, cos);
    	m.setValue(3, 1, -sin);
    	m.setValue(1, 3, sin);
    	return m;
    }
    
    public static Matrix rotateAroundZMatrix(float angle) {
    	Matrix m = Matrix.identity(4);
    	float sin = (float)Math.sin(angle);
    	float cos = (float)Math.cos(angle);
    	m.setValue(1, 1, cos);
    	m.setValue(2, 2, cos);
    	m.setValue(2, 1, sin);
    	m.setValue(1, 2, -sin);
    	return m;
    }
    
    public static Matrix rotateAroundXMatrix(Matrix old, float angle) {
    	return Matrix.multiply(rotateAroundXMatrix(angle), old);
    }
    
    public static Matrix rotateAroundYMatrix(Matrix old, float angle) {
    	return Matrix.multiply(rotateAroundYMatrix(angle), old);
    }
    
    public static Matrix rotateAroundZMatrix(Matrix old, float angle) {
    	return Matrix.multiply(rotateAroundZMatrix(angle), old);
    }
    
    //rotate around axis
    
    public static Matrix rotateAroundAxisMatrix(Line3d line, float angle) {
    	Point3d center = line.getBase();
    	Vector3d slope = line.getSlope().clone();
    	Matrix matrix = translateMatrix(-center.getX(), -center.getY(), -center.getZ());
    	
    	float angleX = slope.getAngleX();
    	matrix = rotateAroundXMatrix(matrix, -angleX);
    	slope.rotateX(-angleX);
    	
    	float angleY = slope.getAngleY() - (float)(Math.PI / 2F); //put it on Z axis
    	matrix = rotateAroundYMatrix(matrix, -angleY);
    	
    	matrix = rotateAroundZMatrix(matrix, angle); //rotation takes place here
    	
    	matrix = rotateAroundYMatrix(matrix, angleY);
    	matrix = rotateAroundXMatrix(matrix, angleX);
    	matrix = translateMatrix(matrix, center);
    	return matrix;
    }
    
    //object
    
    private Matrix matrix;
    
    public MatrixTransformation3d() {
    	this(Matrix.identity(4));
    }
    
    public MatrixTransformation3d(Matrix m) {
    	if(m.isSquare() && m.getRows() == 4) {
    		matrix = m;
    	} else {
    		throw new IllegalArgumentException("Three-dimensional transformations require a 4x4 matrix");
    	}
    }
    
    public void translate(Point3d p) {
    	matrix = translateMatrix(matrix, p);
    }
    
    public void translate(Vector3d v) {
    	matrix = translateMatrix(matrix, v);
    }
    
    public void translate(float x, float y, float z) {
    	matrix = translateMatrix(matrix, x, y, z);
    }
    
    public void scale(float scale) {
    	matrix = scaleMatrix(matrix, scale);
    }
    
    public void scale(float x, float y, float z) {
    	matrix = scaleMatrix(matrix, x, y, z);
    }
    
    public void rotateAroundX(float angle) {
    	matrix = rotateAroundXMatrix(matrix, angle);
    }
    
    public void rotateAroundY(float angle) {
    	matrix = rotateAroundYMatrix(matrix, angle);
    }
    
    public void rotateAroundZ(float angle) {
    	matrix = rotateAroundZMatrix(matrix, angle);
    }
    
    public void rotateAroundAxis(Line3d axis, float angle) {
    	Matrix m = rotateAroundAxisMatrix(axis, angle);
    	m.multiply(matrix);
    	matrix = m;
    }
    
    public Vector3d transform(Vector3d v) {
    	return new Vector3d(Matrix.multiply(matrix, v.toMatrix(true)));
    }
    
    //returns Vector for speed
    public Vector3d transform(Point3d p) {
    	return transform(new Vector3d(p));
    }
    
    public Matrix getMatrix() {
    	return matrix;
    }

}
