% Read data from CSV files
eulerData = csvread('/Users/thomasdls/Documents/GitHub/Group08_MM/euler_results.csv', 1, 0);
rungeKuttaData = csvread('/Users/thomasdls/Documents/GitHub/Group08_MM/rk_results.csv', 1, 0);
adamsMoultonData = csvread('/Users/thomasdls/Documents/GitHub/Group08_MM/am_results.csv', 1, 0);

% Extract relevant columns
stepSizeEuler = eulerData(:, 1);
relativeErrorEuler = eulerData(:, 2);

stepSizeRungeKutta = rungeKuttaData(:, 1);
relativeErrorRungeKutta = rungeKuttaData(:, 2);

stepSizeAdamsMoulton = adamsMoultonData(:, 1);
relativeErrorAdamsMoulton = adamsMoultonData(:, 2);

% Sort data points by step size
[stepSizeEuler, idx] = sort(stepSizeEuler);
relativeErrorEuler = relativeErrorEuler(idx);

[stepSizeRungeKutta, idx] = sort(stepSizeRungeKutta);
relativeErrorRungeKutta = relativeErrorRungeKutta(idx);

[stepSizeAdamsMoulton, idx] = sort(stepSizeAdamsMoulton);
relativeErrorAdamsMoulton = relativeErrorAdamsMoulton(idx);

% Plot on log-log scale
figure;
loglog(stepSizeEuler, relativeErrorEuler, 'o-', 'DisplayName', 'Euler Method');
hold on;
loglog(stepSizeRungeKutta, relativeErrorRungeKutta, 'o-', 'DisplayName', 'Runge-Kutta Method');
loglog(stepSizeAdamsMoulton, relativeErrorAdamsMoulton, 'o-', 'DisplayName', 'Adams-Moulton Method');
hold off;

xlabel('Step Size');
ylabel('Relative Error');
title('Comparison of Different Methods');

legend('Location', 'best');
