import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { SalesChannelDetailComponent } from 'app/entities/sales-channel/sales-channel-detail.component';
import { SalesChannel } from 'app/shared/model/sales-channel.model';

describe('Component Tests', () => {
  describe('SalesChannel Management Detail Component', () => {
    let comp: SalesChannelDetailComponent;
    let fixture: ComponentFixture<SalesChannelDetailComponent>;
    const route = ({ data: of({ salesChannel: new SalesChannel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [SalesChannelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SalesChannelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SalesChannelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load salesChannel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.salesChannel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
