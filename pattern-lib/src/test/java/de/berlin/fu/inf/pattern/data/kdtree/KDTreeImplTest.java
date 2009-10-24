package de.berlin.fu.inf.pattern.data.kdtree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class KDTreeImplTest {
	private KDTreeImpl<DimensionableImpl> kdTree;
	private List<DimensionableImpl> values;
	
	@Before
	public void setUp() throws Exception {
		
		kdTree = new KDTreeImpl<DimensionableImpl>(DimensionableImpl.DIMENSIONS);
		
		values = new ArrayList<DimensionableImpl>(5);
		
		values.add(new DimensionableImpl(new int[]{3,3,3}));
		values.add(new DimensionableImpl(new int[]{1,2,2}));
		values.add(new DimensionableImpl(new int[]{5,1,5}));
		values.add(new DimensionableImpl(new int[]{4,4,1}));
		values.add(new DimensionableImpl(new int[]{2,7,4}));
		
	}
	
	@Test
	public void getMedianBetween() {
		assertTrue(kdTree.getMedianBetween(0, 0) == 0);
		assertTrue(kdTree.getMedianBetween(0, 1) == 1);
		assertTrue(kdTree.getMedianBetween(0, 2) == 1);
		assertTrue(kdTree.getMedianBetween(0, 3) == 2);
		assertTrue(kdTree.getMedianBetween(0, 4) == 2);
		assertTrue(kdTree.getMedianBetween(0, 5) == 3);
		assertTrue(kdTree.getMedianBetween(1, 1) == 1);
		assertTrue(kdTree.getMedianBetween(1, 2) == 2);
		assertTrue(kdTree.getMedianBetween(1, 3) == 2);
		assertTrue(kdTree.getMedianBetween(1, 4) == 3);
		assertTrue(kdTree.getMedianBetween(1, 5) == 3);
		assertTrue(kdTree.getMedianBetween(5, 1) == -1);
	}
	
	@Test
	public void buildTreeTest() {
		kdTree.buildTree(values.toArray(new DimensionableImpl[0]));
		
		Node<DimensionableImpl> root = kdTree.getRootNode();
		
		assertTrue(root.getContent()==values.get(0));
		assertTrue(root.getLeftNode().getContent() == values.get(4));
		assertTrue(root.getRightNode().getContent() == values.get(3));
		
	}
	
	@Test
	public void findLeaf() {
		kdTree.buildTree(values.toArray(new DimensionableImpl[0]));
		
		for(DimensionableImpl v : values){
			assertEquals(v, kdTree.findLeaf(v));
		}
	}
	 
	@Test
	public void findNeighbour() {
		kdTree.buildTree(values.toArray(new DimensionableImpl[0]));
		
		for(DimensionableImpl v : values){
			assertEquals(v, kdTree.findNearestValues(v));
		}
		assertEquals(
				new DimensionableImpl(new int[]{5,1,5}),
				kdTree.findNearestValues(new DimensionableImpl(new int[]{6,1,5})));
		assertEquals(
				new DimensionableImpl(new int[]{3,3,3}),
				kdTree.findNearestValues(new DimensionableImpl(new int[]{4,4,4})));
		assertEquals(
				new DimensionableImpl(new int[]{3,3,3}),
				kdTree.findNearestValues(new DimensionableImpl(new int[]{3,4,3})));
		assertEquals(
				new DimensionableImpl(new int[]{1,2,2}),
				kdTree.findNearestValues(new DimensionableImpl(new int[]{2,2,2})));
		assertEquals(
				new DimensionableImpl(new int[]{3,3,3}),
				kdTree.findNearestValues(new DimensionableImpl(new int[]{3,2,2})));
	}
}
