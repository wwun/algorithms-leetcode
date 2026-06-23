import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import javax.swing.tree.TreeNode;


public class Pr{

    public Pr(){
        twoSum(new int[]{3,2,4}, 6);
    }

    // 1. Two Sum
    // Given an array of integers nums and an integer target, return indices of the 
    // two numbers such that they add up to target
    public int[] twoSum(int[] nums, int target){
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    // 78. Subset
    // Given an integer array nums of unique elements, return all possible subsets
    //public List<List<Integer>> subsets(int[] nums){
        
    //}

    // 3. Longest Substring Without Repeating Characters (Sliding)
    // Given a string s, find the length of the longest without duplicate characters
    public int lengthOfLongestSubstring(String s){

        int left = 0, right = 0, max = 0;
        Set<Character> window = new HashSet<>();

        while(right<s.length()){
            char current = s.charAt(right);
            if(!window.contains(current)){
                window.add(current);
                right++;
                max = Math.max(max, right - left);
            }else{                
                window.remove(s.charAt(left));
                left++;
            }

        }

        return max;

    }

    // 20. Valid Parentheses
    // Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', 
    // determine if the input string is valid
    public boolean isValid(String braces){
        if(braces == null || braces.isEmpty())
            return false;

        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put('}', '{');
        pairs.put(']', '[');
        
        Deque<Character> stack = new ArrayDeque<>();

        for(int i=0; i<braces.length(); i++){
            Character val = braces.charAt(i);
            if("({[".contains(val.toString()))
                stack.push(val);
            else if(")}]".contains(val.toString()))
                if(stack.isEmpty() || stack.pop() != pairs.get(val))
                    return false;
        }

        return stack.isEmpty();
    }

    // 21. Merge Two Sorted List
    // you are given the heads of two sorted linked lists list1 and list2
    // Merge the two lists into one sorted list
    public ListNode mergeTwoLists(ListNode list1, ListNode list2){
        ListNode head = new ListNode();
        ListNode current = head;
        while(list1!=null && list2!=null){
            if(list1.val<=list2.val){
                current.next = list1;
                list1 = list1.next;
                current = current.next;
            }else{
                current.next = list2;
                list2 = list2.next;
                current = current.next;
            } 
        }
        current.next = list1 != null ? list1 : list2;
        return head.next;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }



    // 55. 3Sum
    // Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] 
    // such that i != j, i != k, and j != k, 
    // and nums[i] + nums[j] + nums[k] == 0

    public List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> outputArray = new ArrayList<>();
        if(nums == null || nums.length < 3)
            return outputArray;
        Arrays.sort(nums);
        for(int i=0; i<nums.length-2; i++){
            if (nums[i] > 0) {
                break;
            }

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    outputArray.add(List.of(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                }else{
                    right--;
                }
            }
        }
        return outputArray;
    }

    // 100. Same Tree
    // Given the roots of two binary trees p and q, write a function to check if they are 
    // the same or not, two binary trees are considered the same if they are structurally 
    // identical, and the nodes have the same value
    public boolean isSameTree(TreeNode p, TreeNode q){
        if(p == null && q == null)
            return true;
        if(p == null || q == null)
            return false;
        if(p.val != q.val)
            return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right); 
    }

    // 102. Binary Tree Level Order Traversal
    // Given the root of a binary tree, return the level order traversal of its nodes' values
    public List<List<Integer>> levelOrder(TreeNode root){

        List<List<Integer>> outputList = new ArrayList<>();
        if(root == null) return outputList;

        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            for(int i=0; i<levelSize; i++){
                TreeNode current = queue.poll();
                level.add(current.val);
                if(current.left != null)
                    queue.add(current.left);
                if(current.right != null)
                    queue.add(current.right);
            }
            outputList.add(level);
        }

        return outputList;

    }

    // 104. Maximum Depth of Binary Tree
    // Given the root of a binary tree, return its maximum depth
    // A binary tree's maximum depth is the number of nodes along the longest path from the root 
    // node down to the farthest leaf nod
    public int maxDepthBFS(TreeNode root) {

        if(root == null)
            return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        int count = 0;
        
        queue.add(root);

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            for(int i=0; i<levelSize; i++){
                TreeNode current = queue.poll();
                if(current.right != null)
                    queue.add(current.right);
                if(current.left != null)
                    queue.add(current.left);
                
            }
            count++;
        }

        return count;
    }

    public int maxDepthDFS(TreeNode root) {
        if (root == null) return 0;  

        int leftDepth = maxDepthDFS(root.left);    
        int rightDepth = maxDepthDFS(root.right);  
        return 1 + Math.max(leftDepth, rightDepth);
    }

    // 125. Valid Palindrome
    // Ahrase is a palindrome if, after converting all uppercase letters into lowercase letters 
    // and removing all non-alphanumeric characters, it reads the same forward and backward, 
    // alphanumeric characters include letters and numbers, given a string s, return true if it 
    // is a palindrome, or false otherwise
    public boolean isPalindrome(String s){
        int left = 0;
        int right = s.length() - 1;

        while(left < right){
            while(left < right && !Character.isLetterOrDigit(s.charAt(left)))
                left++;
            while(left < right && !Character.isLetterOrDigit(s.charAt(right)))
                right--;
            if(Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right)))
                return false;
            left++;
            right--;
        }
        
        return true;

    }

    // 167. Two Sum II - Input Array Is Sorted
    public int[] twoSumII(int[] numbers, int target){
        int i=0, k=numbers.length-1;
        while(i<k){
            int sum = numbers[i]+numbers[k];
            if(sum==target){
                return new int[]{ i+1, k+1};
            }
            if(sum>target){
                k--;
            }else{
                i++;
            }
        }
        return new int[]{};
    }    

    // 200. Number of Islands (DFS)
    // Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's 
    // (water), return the number of islands

    public int numIslands(char[][] grid){
        
        int count = 0;

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == '1'){
                    dfs(grid, i, j);
                    count++;
                }
            }
        }

        return count;

    }

    public void dfs(char[][] grid, int i, int j){

        if(i<0 || i>=grid.length) return;
        if(j<0 || j>=grid[0].length) return;
        if(grid[i][j] != '1') return;

        grid[i][j] = '0';

        dfs(grid, i+1, j);
        dfs(grid, i-1, j);
        dfs(grid, i, j+1);
        dfs(grid, i, j-1);

    }

    // 206. Reverse Linked List
    // Given the head of a singly linked list, reverse the list, and return 
    // the reversed list

    public ListNode reverseList(ListNode head){

        if(head == null || head.next == null)
            return head;

        ListNode previous = null;
        ListNode current = head;
        ListNode next;

        while(current != null){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        return previous;
    }



    // 215. Kth Largest Element in an Array
    // Given an integer array nums and an integer k, return the kth largest element in the array

    public int findKthLargest(int[] nums, int k){

        PriorityQueue<Integer> pq = new PriorityQueue<>(
            (first, second) -> first - second
        );

        for(int num : nums){

            pq.add(num);

            if(pq.size()>k){
                pq.poll();
            }

        }

        return pq.peek();
    }

    // 217. Contains Duplicate
    public boolean containsDuplicate(int[] nums){
        if(nums == null)
            return false;
        Set<Integer> duplicates = new HashSet<>();
        for(int i=0; i<nums.length; i++){
            if(!duplicates.add(nums[i]))
                return true;
        }
        return false;
    }

    // 226. Invert Binary Tree
    // Given the root of a binary tree, invert the tree, and return its root
    public TreeNode invertTree(TreeNode root){
        if(root == null)
            return root;
        
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        
        invertTree(root.left);
        invertTree(root.right);
        
        return root;

    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 242. Valid Anagram
    // Given two strings s and t, return true if t is an of s, and false otherwise
    public boolean isAnagram(String s, String t) {
        if(s == null || t == null || s.length() != t.length())
            return false;
        Map<Character, Integer> original = new HashMap<>();
        for(int i=0; i<s.length(); i++){
            original.put(s.charAt(i), original.getOrDefault(s.charAt(i), 0) + 1);
        }
        for(int i=0; i<t.length(); i++){
            char val = t.charAt(i);
            if(!original.containsKey(val) || original.get(val) == 0)
                return false;
            original.put(val, original.get(val) - 1);
        }
        return true;
    }

    // 303. Range Sum Query - Immutable
    // Given an integer array nums, handle multiple queries of the following type
    // Calculate the sum of the elements of nums between indices left and right inclusive 
    // where left <= right
    // Implement the NumArray class
    // NumArray(int[] nums) Initializes the object with the integer array nums,
    // int sumRange(int left, int right) Returns the sum of the elements of nums between 
    // indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right
    class NumArray {

        private int[] accumulative;
        

        public NumArray(int[] nums) {
            accumulative= new int[nums.length+1];
            accumulative[0] = 0;
            for(int i=0; i<nums.length; i++){
                accumulative[i+1] = accumulative[i] + nums[i];
            }
        }
        
        public int sumRange(int left, int right) {
            return accumulative[right + 1] - accumulative[left ];
        }
    }

    // 347. Top K Frequent Elements
    // Given an integer array nums and an integer k, return the k most frequent elements, you may return the answer in any order
    
    public int[] topKFrequent(int[] nums, int k) {
        
        Map<Integer, Integer> frecuencies = new HashMap<>();
        
        for(int num : nums){
            frecuencies.put(num, frecuencies.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(
            (first, second) -> first.getValue() - second.getValue()
        );

        for(Map.Entry<Integer, Integer> pair : frecuencies.entrySet()){
            minHeap.offer(pair);
            if(minHeap.size()>k){
                minHeap.poll();
            }
        }

        int[] response = new int[k];

        for(int i=0; i<k; i++){
            response[i] = minHeap.poll().getKey();            
        }

        return response;

    }

    public int[] topKFrequent2(int[] nums, int k){
        Map<Integer, Integer> freq = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            freq.put(nums[i], freq.getOrDefault(nums[i], 0) + 1);
        }

        Queue<Integer> prq = new PriorityQueue<>((a, b) -> freq.get(b) - freq.get(a));
        for(int num : freq.keySet()){
            prq.add(num);
        }

        int[] numsTopK = new int[k];
        for(int i=0; i<k; i++)
            numsTopK[i] = prq.poll(); 
        
        return numsTopK;
    }
    
    // 704. Binary Search
    // Given an array of integers nums which is sorted in ascending order, and an integer target, 
    // write a function to search target in nums, if target exists, then return its index, 
    // otherwise, return -1
    // you must write an algorithm with O(log n) runtime complexit
    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while(start <= end){
            int middle = start + (end - start)/2;
            if(target == nums[middle])
                return middle;
            if(nums[middle] < target)
                start = middle + 1;
            else
                end = middle - 1;
        }
        return -1;
    }

}
