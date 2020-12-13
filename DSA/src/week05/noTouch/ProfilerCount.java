//package week05.noTouch;
//
//import java.awt.Color;
//
//import org.apache.commons.math3.stat.regression.SimpleRegression;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.data.xy.XYDataItem;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.RefineryUtilities;
//
///**
// * @author sblee edited for plotting counter
// *
// */
//public class ProfilerCount extends ApplicationFrame { // 그림 그리는 것.
//
//	/**
//	 * This is here because extending ApplicationFrame requires it.
//	 */
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * Timeable defines the methods an object must provide to work with Profiler
//	 *
//	 */
//	public interface Figurable {
//		
//		public void setup(int n);
//
//		public double evalMe(int n);
//	}
//
//	private Figurable estimated;
//
//	public ProfilerCount(String title, Figurable rundata) {
//		super(title);
//		this.estimated = rundata;
//	}
//
//	/**
//	 * Invokes timeIt with a range of `n` from `startN` until runtime exceeds `endMillis`.
//	 *
//	 * @param data.timeable
//	 * @param n
//	 * @return
//	 */
//	public XYSeries countingLoop(int startN, int endCount) {
//        final XYSeries series = new XYSeries("Average Hop Count");
//
//		for (int i=startN; i<endCount; i++) {
//			double tmp= countIt(i);
//			System.out.println(i + ", " + tmp);
//			series.add(i, tmp);
//		}
//		return series;
//	}
//
//	/**
//	 * Invokes setup and timeMe on the embedded Countable.
//	 *
//	 * @param n
//	 * @return
//	 */
//	public double countIt(int n) {
//		estimated.setup(n);
////		final long startTime = System.currentTimeMillis();
//		double tmp = estimated.evalMe(n);
////		final long endTime = System.currentTimeMillis();
//		return tmp;
//	}
//
//	/**
//	 * Plots the results.
//	 *
//	 * @param series
//	 */
//	public void plotResults(XYSeries series) {
//		double slope = estimateSlope(series);
//		System.out.println("Estimated slope= " + slope);
//
//		final XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(series);
//
//        final JFreeChart chart = ChartFactory.createXYLineChart(
//            "",          // chart title
//            "",               // domain axis label
//            "",                  // range axis label
//            dataset,                  // data
//            PlotOrientation.VERTICAL,
//            false,                     // include legend
//            true,
//            false
//        );
//
//        final XYPlot plot = chart.getXYPlot();
//        final NumberAxis domainAxis = new NumberAxis("iterations i*20");
//        final NumberAxis rangeAxis = new NumberAxis("Average # Collisions Happened");
//        plot.setDomainAxis(domainAxis);
//        plot.setRangeAxis(rangeAxis);
//        chart.setBackgroundPaint(Color.white);
//        plot.setOutlinePaint(Color.black);
//        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 600));
//        setContentPane(chartPanel);
//        pack();
//        RefineryUtilities.centerFrameOnScreen(this);
//        setVisible(true);
//	}
//
//	/**
//	 * Uses simple regression to estimate the slope of the series.
//	 *
//	 * @param series
//	 * @return
//	 */
//	public double estimateSlope(XYSeries series) {
//		SimpleRegression regression = new SimpleRegression();
//
//		for (Object item: series.getItems()) {
//			XYDataItem xy = (XYDataItem) item;
//			regression.addData(xy.getXValue(), (xy.getYValue()));
//		}
//		return regression.getSlope();
//	}
//}
//
