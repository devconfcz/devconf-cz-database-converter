package cz.devconf.model;

public class Speaker {

    private String id;
    private String avatar;
    private String name;
    private String email;
    private String country;
    private String bio;
    private String organization;
    private String twitter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public static String generateIdFromEmail(String email) {
        String speakerEmail = email.trim().toLowerCase();
        return speakerEmail.substring(0, speakerEmail.indexOf("@")).replace(".", "_");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Speaker speaker = (Speaker) o;

        return email.equals(speaker.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

}
