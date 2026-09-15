<template>
  <aside class="live-stage-panel">
    <div class="panel-head">
      <span class="panel-title">思考过程</span>
      <span v-if="running" class="panel-live">进行中</span>
      <span v-else-if="items.length" class="panel-done">已完成</span>
    </div>
    <div v-if="!items.length" class="panel-empty">等待管线开始…</div>
    <ol v-else class="stage-list">
      <li
        v-for="item in items"
        :key="item.name"
        class="stage-item"
        :class="'is-' + item.status"
      >
        <span class="stage-mark">
          <span v-if="item.status === 'running'" class="spin"></span>
          <span v-else-if="item.status === 'done'">✓</span>
          <span v-else-if="item.status === 'skipped'">–</span>
          <span v-else>!</span>
        </span>
        <span class="stage-label">{{ item.label }}</span>
        <span v-if="item.status === 'running'" class="stage-cost ticking">{{ elapsedText }}</span>
        <span v-else-if="item.costMs != null" class="stage-cost">{{ formatMs(item.costMs) }}</span>
        <span v-else-if="item.status === 'skipped'" class="stage-cost muted">跳过</span>
      </li>
    </ol>
    <div v-if="totalText" class="panel-total">累计 {{ totalText }}</div>
  </aside>
</template>

<script setup lang="ts">
  import { computed, onBeforeUnmount, onMounted, ref, watch } from 'vue';
  import type { LiveStageItem } from '@/api/system/agent';

  const props = defineProps<{
    items: LiveStageItem[];
    running?: boolean;
  }>();

  const now = ref(Date.now());
  let timer: ReturnType<typeof setInterval> | null = null;
  const runStartedAt = ref(0);

  watch(
    () => props.running,
    (v) => {
      if (v) runStartedAt.value = Date.now();
    },
    { immediate: true }
  );

  onMounted(() => {
    timer = setInterval(() => {
      now.value = Date.now();
    }, 200);
  });
  onBeforeUnmount(() => {
    if (timer) clearInterval(timer);
  });

  function formatMs(ms: number): string {
    if (ms < 1000) return `${ms}ms`;
    return `${(ms / 1000).toFixed(2)}s`;
  }

  const elapsedText = computed(() => {
    const current = props.items.find((i) => i.status === 'running');
    if (!current) return '';
    const doneMs = props.items
      .filter((i) => i.status === 'done' && i.costMs != null)
      .reduce((s, i) => s + (i.costMs || 0), 0);
    const elapsed = Math.max(0, now.value - runStartedAt.value - doneMs);
    return formatMs(elapsed);
  });

  const totalText = computed(() => {
    const doneMs = props.items
      .filter((i) => i.status === 'done' && i.costMs != null)
      .reduce((s, i) => s + (i.costMs || 0), 0);
    if (!doneMs && !props.running) return '';
    if (props.running) {
      const elapsed = Math.max(doneMs, now.value - runStartedAt.value);
      return formatMs(elapsed);
    }
    return formatMs(doneMs);
  });
</script>

<style lang="less" scoped>
  .live-stage-panel {
    width: 280px;
    flex-shrink: 0;
    height: 100%;
    border-left: 1px solid #e5e6eb;
    background: #fafafa;
    display: flex;
    flex-direction: column;
    padding: 14px 14px 16px;
    box-sizing: border-box;
    overflow: hidden;
  }
  .panel-head {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 12px;
    flex-shrink: 0;
  }
  .panel-title {
    font-size: 14px;
    font-weight: 600;
    color: #1d2129;
  }
  .panel-live {
    font-size: 11px;
    color: #07c05f;
    background: rgba(7, 192, 95, 0.1);
    padding: 2px 8px;
    border-radius: 10px;
  }
  .panel-done {
    font-size: 11px;
    color: #86909c;
    background: #f2f3f5;
    padding: 2px 8px;
    border-radius: 10px;
  }
  .panel-empty {
    font-size: 13px;
    color: #86909c;
    padding: 24px 0;
    text-align: center;
  }
  .stage-list {
    list-style: none;
    margin: 0;
    padding: 0;
    overflow-y: auto;
    flex: 1;
  }
  .stage-item {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 4px;
    font-size: 13px;
    color: #4e5969;
    border-bottom: 1px solid #f2f3f5;
    &.is-running {
      color: #07c05f;
      font-weight: 600;
    }
    &.is-done {
      color: #1d2129;
    }
    &.is-skipped {
      color: #c9cdd4;
    }
    &.is-error {
      color: #d03050;
    }
  }
  .stage-mark {
    width: 16px;
    text-align: center;
    flex-shrink: 0;
    font-size: 12px;
  }
  .stage-label {
    flex: 1;
    min-width: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .stage-cost {
    font-size: 12px;
    color: #86909c;
    font-variant-numeric: tabular-nums;
    flex-shrink: 0;
    &.ticking {
      color: #07c05f;
    }
    &.muted {
      color: #c9cdd4;
    }
  }
  .spin {
    display: inline-block;
    width: 10px;
    height: 10px;
    border: 2px solid rgba(7, 192, 95, 0.25);
    border-top-color: #07c05f;
    border-radius: 50%;
    animation: live-spin 0.7s linear infinite;
  }
  .panel-total {
    margin-top: 10px;
    font-size: 12px;
    color: #86909c;
    text-align: right;
    flex-shrink: 0;
  }
  @keyframes live-spin {
    to {
      transform: rotate(360deg);
    }
  }
</style>
