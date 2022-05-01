package netology.data;

import lombok.Value;

@Value
public class Card {
    String id;
    String userId;
    String number;
    long balanceInKopecks;
}
