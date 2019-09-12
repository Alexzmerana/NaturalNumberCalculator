import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Alex Merana
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE),
            ZERO = new NaturalNumber2();

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {
        NaturalNumber zero = new NaturalNumber2();
        if (model.bottom().compareTo(zero) == 0) {
            view.updateDivideAllowed(false);
        } else {
            view.updateDivideAllowed(true);
        }

        if (model.bottom().compareTo(INT_LIMIT) <= 0) {
            view.updatePowerAllowed(true);
        } else {
            view.updatePowerAllowed(false);
        }

        if (model.bottom().compareTo(INT_LIMIT) <= 0
                && model.bottom().compareTo(TWO) >= 0) {
            view.updateRootAllowed(true);
        } else {
            view.updateRootAllowed(false);
        }
        if (model.top().compareTo(model.bottom()) >= 0) {
            view.updateSubtractAllowed(true);
        } else {
            view.updateSubtractAllowed(false);
        }
        view.updateBottomDisplay(model.bottom());
        view.updateTopDisplay(model.top());
    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {

        this.model.top().copyFrom(this.model.bottom());
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bot = this.model.bottom();
        top.add(bot);
        bot.transferFrom(top);
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bot = this.model.bottom();
        top.subtract(bot);
        bot.transferFrom(top);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processMultiplyEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bot = this.model.bottom();
        top.multiply(bot);
        bot.transferFrom(top);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processDivideEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bot = this.model.bottom();
        NaturalNumber remainder = new NaturalNumber2(top.divide(bot));
        bot.transferFrom(top);
        top.transferFrom(remainder);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processPowerEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bot = this.model.bottom();
        int power = bot.toInt();
        top.power(power);
        bot.transferFrom(top);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processRootEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bot = this.model.bottom();
        int root = bot.toInt();
        top.root(root);
        bot.transferFrom(top);
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAddNewDigitEvent(int digit) {
        this.model.bottom().multiplyBy10(digit);
        updateViewToMatchModel(this.model, this.view);

    }

}
