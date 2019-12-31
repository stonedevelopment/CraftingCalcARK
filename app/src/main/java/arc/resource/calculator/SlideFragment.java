package arc.resource.calculator;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

public final class SlideFragment extends SlideBaseFragment {

    public static SlideFragment newInstance(@StringRes int title, @StringRes int description,
                                            String mainImage) {
        return newInstance(title, description, mainImage, R.layout.content_intro);
    }

    public static SlideFragment newInstance(@StringRes int title, @StringRes int description,
                                            String mainImage, @LayoutRes int layout) {
        SlideFragment slide = new SlideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MAIN_IMAGE, mainImage);
        args.putString(ARG_SELFIE_IMAGE, null);
        args.putInt(ARG_TITLE, title);
        args.putInt(ARG_DESC, description);
        args.putInt(ARG_LAYOUT, layout);
        slide.setArguments(args);

        return slide;
    }

    public static SlideFragment newInstance(@StringRes int title, @StringRes int description,
                                            String mainImage, String selfieImage,
                                            @LayoutRes int layout) {
        SlideFragment slide = new SlideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MAIN_IMAGE, mainImage);
        args.putString(ARG_SELFIE_IMAGE, selfieImage);
        args.putInt(ARG_TITLE, title);
        args.putInt(ARG_DESC, description);
        args.putInt(ARG_LAYOUT, layout);
        slide.setArguments(args);

        return slide;
    }
}
