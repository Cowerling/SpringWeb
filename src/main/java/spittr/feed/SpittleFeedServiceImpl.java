package spittr.feed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import spittr.Notification;
import spittr.Spittle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SpittleFeedServiceImpl implements SpittleFeedService {
    private static final Logger logger = LoggerFactory.getLogger(SpittleFeedServiceImpl.class);

    private Pattern pattern = Pattern.compile("\\@(\\S+)");

    @Autowired
    private SimpMessageSendingOperations messageSendingOperations;

    @Override
    public void broadcastSpittle(Spittle spittle) {
        logger.info("broadcastSpittle(" + spittle + ")");
        messageSendingOperations.convertAndSend("/topic/spittlefeed", spittle);

        Matcher matcher = pattern.matcher(spittle.getMessage());
        if (matcher.find()) {
            String username = matcher.group(1);
            messageSendingOperations.convertAndSendToUser(username, "/queue/notifications", new Notification("You just got mentioned!"));
        }
    }
}
