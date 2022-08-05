import { IPerfReview } from 'app/shared/model/perf-review.model';
import { IPerfRatingType } from 'app/shared/model/perf-rating-type.model';
import { IPerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';
import { IParty } from 'app/shared/model/party.model';

export interface IPerfReviewItem {
  id?: number;
  sequenceNo?: number;
  comments?: string;
  perfReview?: IPerfReview;
  perfRatingType?: IPerfRatingType;
  type?: IPerfReviewItemType;
  employee?: IParty;
}

export class PerfReviewItem implements IPerfReviewItem {
  constructor(
    public id?: number,
    public sequenceNo?: number,
    public comments?: string,
    public perfReview?: IPerfReview,
    public perfRatingType?: IPerfRatingType,
    public type?: IPerfReviewItemType,
    public employee?: IParty
  ) {}
}
