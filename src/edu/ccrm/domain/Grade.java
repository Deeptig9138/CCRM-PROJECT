package edu.ccrm.domain;

public enum Grade {
    S(4.0), A(4.0), B(3.0), C(2.0), D(1.0), F(0.0);

    private final double points;
    Grade(double points) { this.points = points; }
    public double getPoints() { return points; }

    public static Grade fromMarks(double marks) {
        if (marks >= 90) return S;
        if (marks >= 80) return A;
        if (marks >= 70) return B;
        if (marks >= 60) return C;
        if (marks >= 50) return D;
        return F;
    }
}