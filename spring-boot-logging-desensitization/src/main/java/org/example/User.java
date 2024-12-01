package org.example;

import java.util.StringJoiner;

final class User {
    private final String name;
    private final String password;
    private final String email;
    private final String phone;

    User(String name, String password, String email, String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
            .add("name='" + name + "'")
            .add("password='" + password + "'")
            .add("email='" + email + "'")
            .add("phone='" + phone + "'")
            .toString();
    }
}
