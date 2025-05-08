package io.github.jagodevreede.demo.nohassle.text;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is filled in memory for demo purpose only.
 */
@ApplicationScoped
public class MemeTextRepository {
    private static final Map<UUID, MemeText> DATABASE = initDB();

    public List<MemeText> findAll() {
        return new ArrayList<>(DATABASE.values());
    }

    public MemeText findById(UUID id) {
        return DATABASE.get(id);
    }

    public void save(MemeText memeText) {
        DATABASE.put(memeText.id(), memeText);
    }

    private static Map<UUID, MemeText> initDB() {
        List<MemeText> db = new ArrayList<>();
        db.add(new MemeText(UUID.randomUUID(), "Am once again asking", "For your wifi password", "Bernie"));
        db.add(new MemeText(UUID.randomUUID(), "Am once again asking", "To rate sessions", "Bernie"));
        db.add(new MemeText(UUID.randomUUID(), "Am once again asking", "To make a meme", "Bernie"));
        db.add(new MemeText(UUID.randomUUID(), "Am once again asking", "For more coffee", "Bernie"));
        db.add(new MemeText(UUID.randomUUID(), "Am once again asking", "To stop pushing to main", "Bernie"));
        db.add(new MemeText(UUID.randomUUID(), "Am once again asking", "Where the snacks are hidden", "Bernie"));
        db.add(new MemeText(UUID.randomUUID(), "Am once again asking", "To approve my PR", "Bernie"));
        db.add(new MemeText(UUID.randomUUID(), "A live demo", "What can possibly go wrong?", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "When you finish your code", "But it breaks in production", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "Deploying on Friday", "What could go wrong?", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "When the tests pass", "But the app still crashes", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "When you fix one bug", "And create five more", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "When the server is on fire", "But it's not your problem", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "When you say 'It works on my machine'", "And walk away", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "When the deadline is tomorrow", "And you start coding today", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "When you push to main", "Without testing", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "When the client asks for changes", "After deployment", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "When you delete production data", "Instead of test data", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the build to finish", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the PR to be approved", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the tests to pass", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the client to respond", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the deployment to complete", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the meeting to end", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the code review feedback", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the server to restart", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the coffee machine to work", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the next sprint planning", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Bugs", "Bugs everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "Errors", "Errors everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "Merge conflicts", "Merge conflicts everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "Deadlines", "Deadlines everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "Meetings", "Meetings everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "Debugging", "Debugging everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "Logs", "Logs everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "Hotfixes", "Hotfixes everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "Refactoring", "Refactoring everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "TODOs", "TODOs everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "A live demo", "What can possibly go wrong?", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "Am once again asking", "For your attention during my talk", "Bernie"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the speaker to finish", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Slides", "Slides everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "When the projector fails", "Right before your talk", "Disaster-Girl"));
        db.add(new MemeText(UUID.randomUUID(), "Am once again asking", "For the audience to laugh at my joke", "Bernie"));
        db.add(new MemeText(UUID.randomUUID(), "Still waiting", "For the Q&A session to end", "Waiting-Skeleton"));
        db.add(new MemeText(UUID.randomUUID(), "Technical issues", "Technical issues everywhere", "X-X-Everywhere"));
        db.add(new MemeText(UUID.randomUUID(), "When you forget your clicker", "And have to use the keyboard", "Disaster-Girl"));
        return db.stream().collect(Collectors.toMap(MemeText::id, Function.identity()));
    }

}
