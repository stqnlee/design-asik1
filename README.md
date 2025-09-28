Divide and Conquer Algorithms

Assignment Objectives
	•	Implement classic divide-and-conquer algorithms with safe recursion strategies.
	•	Analyze runtime recurrences using Master Theorem (3 cases) and Akra–Bazzi intuition, then validate results through experimental measurements.
	•	Collect performance metrics (execution time, recursion depth, number of comparisons and allocations) and present them in plots with discussion.
	•	Maintain a clean Git history with feature branches and a logical commit storyline.

⸻

Implemented Algorithms
	1.	MergeSort (Divide and Conquer, Master Case 2)
	•	Linear merge with a reusable buffer.
	•	Insertion sort used as a cutoff for small input sizes.
	•	Recursion depth carefully bounded and controlled.
	2.	QuickSort (robust variant)
	•	Randomized pivot selection.
	•	Always recurse into the smaller partition; iterate over the larger one.
	•	Typical recursion depth: O(log n) with high probability.
	3.	Deterministic Select (Median of Medians, O(n))
	•	Partition into groups of 5; use the median of medians as pivot.
	•	In-place partitioning.
	•	Recursive call only on the necessary side (smaller side preferred).
	4.	Closest Pair of Points (2D, O(n log n))
	•	Recursive split by x-coordinate.
	•	Strip check performed in y-order with the classic 7–8 neighbor scan.

⸻

Report Notes
	•	Architecture: recursion depth controlled; allocations minimized using reusable buffers.
	•	Recurrence Analysis:
	•	MergeSort: T(n) = 2T(n/2) + Θ(n) ⇒ Θ(n log n)
	•	QuickSort: T(n) = T(k) + T(n−k−1) + Θ(n) ⇒ expected Θ(n log n)
	•	Select (MoM5): T(n) = T(n/5) + T(7n/10) + Θ(n) ⇒ Θ(n)
	•	Closest Pair: T(n) = 2T(n/2) + Θ(n) ⇒ Θ(n log n)
	•	Plots:
	•	Runtime vs input size.
	•	Recursion depth vs input size.
	•	Comparison of theoretical predictions with empirical data.
	•	Summary: discussion of constant-factor effects (caching, garbage collection) and how practice aligns or diverges from theory.

⸻

Git Workflow
	•	Branches:
	•	main → release versions (v0.1, v1.0).
	•	feature/mergesort, feature/quicksort, feature/select, feature/closest, feature/metrics.
	•	Commit storyline:
	•	init: Maven, JUnit5, CI, README.
	•	feat(metrics): counters, depth tracker, CSV writer.
	•	feat(mergesort): baseline + reusable buffer + cutoff + tests.
	•	feat(quicksort): randomized pivot + smaller-first recursion + tests.
	•	refactor(util): partition, swap, shuffle, guards.
	•	feat(select): deterministic select (MoM5) + tests.
	•	feat(closest): closest pair divide-and-conquer + tests.
	•	feat(cli): argument parsing, run algorithms, export CSV.
	•	bench(jmh): benchmarking harness (select vs sort).
	•	docs(report): recurrence analysis, plots, discussion.
	•	fix: edge cases (duplicates, tiny arrays).
	•	release: v1.0.

⸻

Testing
	•	Sorting: validated on both random and adversarial arrays; recursion depth bounded (QuickSort ≤ O(log² n) with randomized pivot).
	•	Select: compared against Arrays.sort(a)[k] over 100 random trials.
	•	Closest Pair: checked against brute-force O(n²) for small inputs (n ≤ 2000), then verified fast implementation for large inputs.
