import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * <p>Presentation maintains the slides in the presentation.</p>
 * <p>There is only instance of this class.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
	private String title; // title of the presentation
	private List<Slide> slides = null; // an ArrayList with Slides
	private int currentSlideNumber; // the slidenummer of the current Slide
	private List<Observable> observables; // list of observables

	public Presentation(String title)
	{
		this.title = title;
		this.observables = new ArrayList<>();
		this.slides = new ArrayList<>();
		this.currentSlideNumber = 0;
	}

	public int getSize()
	{
		return this.slides.size();
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	// give the number of the current slide
	public int getSlideNumber()
	{
		return currentSlideNumber;
	}

	// change the current slide number and notify the observables
	public void setSlideNumber(int number)
	{
		if (number >= 0 && number < getSize())
		{
			this.currentSlideNumber = number;
			notifyObservables();
		}
	}

	// go to the previous slide unless your at the beginning of the presentation
	public void prevSlide()
	{
		if (currentSlideNumber > 0)
		{
			setSlideNumber(currentSlideNumber - 1);
	    }
	}

	// go to the next slide unless your at the end of the presentation.
	public void nextSlide()
	{
		if (currentSlideNumber < slides.size() - 1)
		{
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	// Delete the presentation to be ready for the next one.
	void clear()
	{
		this.slides = Collections.emptyList();
		setSlideNumber(-1);
	}

	// Add a slide to the presentation
	public void addSlide(Slide slide)
	{
		slides.add(slide);
	}

	// Get a slide with a certain slide number
	public Slide getSlide(int number)
	{
		if (number < 0 || number >= getSize())
		{
			throw new IndexOutOfBoundsException("invalid slide number" + number);
	    }
			return (Slide) slides.get(number);
	}

	// Give the current slide
	public Slide getCurrentSlide()
	{
		return getSlide(currentSlideNumber);
	}

	public void exit(int statusNumber)
	{
		System.exit(statusNumber);
	}

	public void removeObservables(Observable observable)
	{
		this.observables.remove(observable);
	}

	public void addObservables(Observable observable)
	{
		this.observables.add(observable);
	}

	public void notifyObservables()
	{
		for (Observable observable : observables)
		{
			observable.update(this, getCurrentSlide());
		}
	}
}
