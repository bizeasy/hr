import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortProductDetailComponent } from 'app/entities/work-effort-product/work-effort-product-detail.component';
import { WorkEffortProduct } from 'app/shared/model/work-effort-product.model';

describe('Component Tests', () => {
  describe('WorkEffortProduct Management Detail Component', () => {
    let comp: WorkEffortProductDetailComponent;
    let fixture: ComponentFixture<WorkEffortProductDetailComponent>;
    const route = ({ data: of({ workEffortProduct: new WorkEffortProduct(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortProductDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkEffortProductDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkEffortProductDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workEffortProduct on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workEffortProduct).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
