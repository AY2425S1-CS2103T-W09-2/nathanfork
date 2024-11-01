package seedu.edulog.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.edulog.commons.core.index.Index;
import seedu.edulog.commons.util.StringUtil;
import seedu.edulog.logic.parser.exceptions.ParseException;
import seedu.edulog.model.calendar.Day;
import seedu.edulog.model.calendar.Description;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.LessonTime;
import seedu.edulog.model.student.Address;
import seedu.edulog.model.student.Email;
import seedu.edulog.model.student.Fee;
import seedu.edulog.model.student.Name;
import seedu.edulog.model.student.Phone;
import seedu.edulog.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String edulog} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code edulog} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String description}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the description is not within 1 and 100 characters long.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);

        String trimmed = description.trim();
        if (Description.checkEmptyDescription(trimmed)) {
            throw new ParseException(Description.DESCRIPTION_EMPTY);
        } else if (Description.checkTooLongDescription(trimmed)) {
            throw new ParseException(Description.DESCRIPTION_TOO_LONG);
        }

        return new Description(trimmed);
    }

    /**
     * Parse the provided String into a {@code Fee}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the string is invalid.
     */
    public static Fee parseFee(String fee) throws ParseException {
        requireNonNull(fee);
        if (!Fee.isValidFee(fee)) {
            throw new ParseException(Fee.MESSAGE_CONSTRAINTS);
        }
        return new Fee(Integer.parseInt(fee));
    }

    /**
     * Parses a provided String into a {@code Day}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if not spelt as a day of the week or their 3-letter shorthand,
     * like "Monday", "Wednesday", "fri" etc.
     */
    public static Day parseDayOfWeek(String day) throws ParseException {
        requireNonNull(day);
        if (!Day.checkValidDayOfWeek(day)) {
            throw new ParseException(Day.INVALID_DAY_OF_WEEK);
        }
        return new Day(day);
    }

    /**
     * Parses 2 Strings representing a 24-hour time format, the first representing the start time
     * and the second representing the end time of a lesson.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if time provided is either not a 24-hour time format like "1200" or "2359" without spaces,
     * or times provided are invalid when together as determined by {@link LessonTime#checkValidLessonTime(String)}
     */
    public static LessonTime parseLessonTime(String startTime, String endTime) throws ParseException {
        requireAllNonNull(startTime, endTime);

        String startTimeTrimmed = startTime.trim();
        String endTimeTrimmed = endTime.trim();

        if (!LessonTime.checkValidLessonTime(startTimeTrimmed) || !LessonTime.checkValidLessonTime(endTimeTrimmed)) {
            throw new ParseException(LessonTime.NOT_24H_FORMAT);
        }

        if (!LessonTime.checkValidLessonTimes(startTimeTrimmed, endTimeTrimmed)) {
            throw new ParseException(LessonTime.NO_SAME_TIME);
        }

        return new LessonTime(startTimeTrimmed, endTimeTrimmed);
    }
}
